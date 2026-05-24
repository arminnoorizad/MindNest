package ir.armin.mindnest.features.ui.screens.addNoteScreen.components

import androidx.compose.foundation.ScrollState
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import ir.armin.mindnest.data.model.AddEditNoteState
import ir.armin.mindnest.data.model.BackgroundOption

@Composable
fun NoteEditorContent(
    state: AddEditNoteState,
    displayImage: Any?,
    showBackgroundSelector: Boolean,
    backgroundOptions: List<BackgroundOption>,
    scrollState: ScrollState,
    onTitleChange: (TextFieldValue) -> Unit,
    onContentChange: (TextFieldValue) -> Unit,
    onBackgroundSelected: (Int) -> Unit,
    onVoiceTogglePlayback: () -> Unit,
    onVoiceDelete: () -> Unit,
    onImageDelete: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(scrollState),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {

        NoteEditorFields(
            title = state.title,
            content = state.content,
            onTitleChange = onTitleChange,
            onContentChange = onContentChange

        )


        if (displayImage != null) {
            NoteImageAttachment(
                imageModel = displayImage,
                onDeleteClick = onImageDelete
            )
        }

        if (!state.voicePath.isNullOrBlank()) {
            VoiceNotePlayer(
                isPlaying = state.isVoicePlaying,
                positionMs = state.voicePositionMs,
                durationMs = state.voiceDurationMs,
                onTogglePlayback = onVoiceTogglePlayback,
                onDelete = onVoiceDelete
            )
        }



        if (showBackgroundSelector) {
            NoteBackgroundCarousel(
                allOptions = backgroundOptions,
                selectedResId = state.backgroundImageResId,
                onBackgroundSelected = onBackgroundSelected
            )
        }
    }
}
