package ir.armin.mindnest.data.model

import android.net.Uri
import androidx.compose.ui.text.input.TextFieldValue

data class AddEditNoteState(
    val noteId: Int = 0,
    val title: TextFieldValue = TextFieldValue(""),
    val content: TextFieldValue = TextFieldValue(""),
    val backgroundImageResId: Int? = null,
    val imagePath: String? = null,
    val voicePath: String? = null,
    val tempImageUri: Uri? = null,
    val isRecording: Boolean = false,
    val isVoicePlaying: Boolean = false,
    val voiceDurationMs: Long = 0L,
    val voicePositionMs: Long = 0L,
    val isLoading: Boolean = false,
    val isNoteSaved: Boolean = false,
    val error: String? = null
)
