package com.example.purpleNote.viewmodel

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.example.purpleNote.model.database.NoteEntity
import com.example.purpleNote.model.repository.NoteRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
open class NoteViewModel @Inject constructor(
    private val repository: NoteRepository
) : ViewModel() {

    val allNotes = repository.getAllNote().asLiveData()  // دریافت همه یادداشت‌ها
    private val _upsertStatus = MutableLiveData<Boolean>()
    val upsertStatus: LiveData<Boolean> get() = _upsertStatus

    private val _searchResults = MutableLiveData<List<NoteEntity>>()
    val searchResults: LiveData<List<NoteEntity>> get() = _searchResults

    private val _deleteStatus = MutableLiveData<Result<Boolean>>()
    val deleteStatus: LiveData<Result<Boolean>> = _deleteStatus

    // تعریف State برای ذخیره داده نوت خاص
    private val _note = mutableStateOf<NoteEntity?>(null)
    val note: State<NoteEntity?> get() = _note

    // تابع برای گرفتن نوت بر اساس ID
    fun getNoteById(noteId: Int) {
        viewModelScope.launch {
            _note.value = repository.getNoteById(noteId)
        }
    }

    fun upsertNote(
        title: String,
        description: String,
        date: String,
        image: String?,
        voiceUri: String?,
        category: String,
        noteId: Int? = null
    ) = viewModelScope.launch {
        try {
            val note = if (noteId != null) {
                NoteEntity(
                    id = noteId,
                    title = title,
                    description = description,
                    date = date,
                    image = image,
                    voice = voiceUri,
                    category = category
                )
            } else {
                NoteEntity(
                    title = title,
                    description = description,
                    date = date,
                    image = image,
                    voice = voiceUri,
                    category = category
                )
            }
            repository.upsertNote(note)
            _upsertStatus.value = true
        } catch (e: Exception) {
            _upsertStatus.value = false
        }
    }

    fun searchNotes(searchQuery: String) {
        viewModelScope.launch {
            _searchResults.value = repository.searchByTitle("%$searchQuery%")
        }
    }

    fun deleteNote(note: NoteEntity) {
        viewModelScope.launch {
            try {
                repository.deleteNote(note)
                _deleteStatus.value = Result.success(true)
            } catch (e: Exception) {
                _deleteStatus.value = Result.failure(e)
            }
        }
    }
}