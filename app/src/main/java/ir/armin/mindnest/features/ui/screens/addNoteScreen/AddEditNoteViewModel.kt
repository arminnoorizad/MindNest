package ir.armin.mindnest.features.ui.screens.addNoteScreen

import android.net.Uri
import androidx.compose.ui.text.input.TextFieldValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import ir.armin.mindnest.core.common.AppResult
import ir.armin.mindnest.data.local.database.entity.NoteEntity
import ir.armin.mindnest.data.model.AddEditNoteState
import ir.armin.mindnest.data.repository.AnalyticsRepository
import ir.armin.mindnest.data.repository.AttachmentRepository
import ir.armin.mindnest.data.repository.AudioPlayer
import ir.armin.mindnest.data.repository.NoteRepository
import ir.armin.mindnest.features.ui.screens.addNoteScreen.util.NoteContentFormatter
import ir.armin.mindnest.features.ui.screens.addNoteScreen.util.VoiceDurationResolver
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.isActive
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class AddEditNoteViewModel @Inject constructor(
    private val repository: NoteRepository,
    private val attachmentRepository: AttachmentRepository,
    private val audioPlayer: AudioPlayer,
    private val analyticsRepository: AnalyticsRepository,
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val noteId: Int = savedStateHandle.get<Int>("noteId") 
        ?: savedStateHandle.get<String>("noteId")?.toIntOrNull() 
        ?: 0

    private val _state = MutableStateFlow(AddEditNoteState())
    val state: StateFlow<AddEditNoteState> = _state.asStateFlow()

    private val filesPendingDeletion = mutableListOf<String>()
    private var playbackProgressJob: Job? = null

    init {
        if (noteId != 0) {
            observeNote(noteId)
        }
        
        audioPlayer.setOnCompletionListener {
            stopPlaybackProgressUpdates()
            _state.update { it.copy(isVoicePlaying = false, voicePositionMs = 0L) }
        }
    }

    private fun observeNote(id: Int) = viewModelScope.launch {
        _state.update { it.copy(isLoading = true) }
        repository.getNoteById(id).collectLatest { entity ->
            if (entity != null) {
                _state.update {
                    it.copy(
                        noteId = entity.id,
                        title = TextFieldValue(entity.title),
                        content = TextFieldValue(entity.content),
                        imagePath = entity.imagePath,
                        voicePath = entity.voicePath,
                        backgroundImageResId = entity.backgroundImageResId,
                        isLoading = false,
                        error = null
                    )
                }
                entity.voicePath?.let { refreshVoiceDuration(it) }
            } else {
                _state.update {
                    it.copy(isLoading = false, error = "Note not found.")
                }
            }
        }
    }

    fun onTitleChange(newValue: TextFieldValue) {
        _state.update { it.copy(title = newValue) }
    }

    fun onContentChange(newValue: TextFieldValue) {
        _state.update { it.copy(content = newValue) }
    }

    fun applyBoldFormatting() {
        _state.update { it.copy(content = NoteContentFormatter.toggleWrapper(it.content, "**")) }
    }

    fun applyItalicFormatting() {
        _state.update { it.copy(content = NoteContentFormatter.toggleWrapper(it.content, "_")) }
    }

    fun applyBulletList() {
        _state.update { it.copy(content = NoteContentFormatter.prependBulletLine(it.content)) }
    }

    fun saveNote() = viewModelScope.launch {
        val currentState = _state.value
        val titleText = currentState.title.text
        val contentText = currentState.content.text

        if (titleText.isBlank() && contentText.isBlank()) {
            _state.update { it.copy(error = "Note cannot be empty.") }
            return@launch
        }

        _state.update { it.copy(isLoading = true) }
        
        var finalImagePath = currentState.imagePath
        if (currentState.tempImageUri != null) {
            finalImagePath = withContext(Dispatchers.IO) {
                attachmentRepository.copyUriToFile(currentState.tempImageUri)
            }
        }

        val note = NoteEntity(
            id = currentState.noteId,
            title = titleText,
            content = contentText,
            imagePath = finalImagePath,
            voicePath = currentState.voicePath,
            backgroundImageResId = currentState.backgroundImageResId
        )

        when (val result = repository.upsertNote(note)) {
            is AppResult.Success -> {
                _state.update { it.copy(isNoteSaved = true, isLoading = false) }
            }
            is AppResult.Error -> {
                _state.update { it.copy(isLoading = false, error = result.message) }
            }
        }
    }

    fun onImageSelected(uri: Uri?) {
        _state.update { it.copy(tempImageUri = uri) }
    }

    fun onImageDelete() {
        _state.update { it.copy(tempImageUri = null, imagePath = null) }
    }

    fun onBackgroundSelected(resId: Int) {
        _state.update { it.copy(backgroundImageResId = resId) }
    }
    
    fun togglePlayback() {
        val path = _state.value.voicePath ?: return
        if (_state.value.isVoicePlaying) {
            audioPlayer.stopPlayback()
            stopPlaybackProgressUpdates()
            _state.update { it.copy(isVoicePlaying = false) }
        } else {
            if (audioPlayer.startPlayback(path)) {
                _state.update { it.copy(isVoicePlaying = true) }
                startPlaybackProgressUpdates()
            }
        }
    }

    fun onVoiceDelete() {
        _state.update { it.copy(voicePath = null, isVoicePlaying = false) }
    }

    fun toggleRecording() {
        if (_state.value.isRecording) {
            val file = attachmentRepository.stopAudioRecording()
            _state.update { it.copy(isRecording = false, voicePath = file) }
            file?.let { refreshVoiceDuration(it) }
        } else {
            attachmentRepository.startAudioRecording()
            _state.update { it.copy(isRecording = true) }
        }
    }

    private fun startPlaybackProgressUpdates() {
        playbackProgressJob?.cancel()
        playbackProgressJob = viewModelScope.launch {
            // Wait for the player to transition to isPlaying=true
            delay(200) 
            while (isActive && audioPlayer.isPlaying()) {
                _state.update { it.copy(voicePositionMs = audioPlayer.getCurrentPositionMs().toLong()) }
                delay(200)
            }
            // If the loop finishes but we expect to be playing, it means either it finished or failed
            _state.update { it.copy(isVoicePlaying = false) }
        }
    }

    private fun stopPlaybackProgressUpdates() {
        playbackProgressJob?.cancel()
        playbackProgressJob = null
    }

    private fun refreshVoiceDuration(path: String) = viewModelScope.launch(Dispatchers.IO) {
        val duration = VoiceDurationResolver.resolveMillis(path)
        _state.update { it.copy(voiceDurationMs = duration) }
    }

    fun clearError() = _state.update { it.copy(error = null) }
}
