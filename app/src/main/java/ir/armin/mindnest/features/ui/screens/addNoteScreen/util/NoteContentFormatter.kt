package ir.armin.mindnest.features.ui.screens.addNoteScreen.util

import androidx.compose.ui.text.TextRange
import androidx.compose.ui.text.input.TextFieldValue

object NoteContentFormatter {

    fun toggleWrapper(value: TextFieldValue, wrapper: String): TextFieldValue {
        val selection = value.selection
        val text = value.text
        
        return if (selection.collapsed) {
            val newText = StringBuilder(text).insert(selection.start, wrapper).toString()
            val newCursor = selection.start + wrapper.length
            value.copy(
                text = newText,
                selection = TextRange(newCursor)
            )
        } else {
            val selectedText = text.substring(selection.start, selection.end)
            
            val (newText, newSelection) = if (selectedText.startsWith(wrapper) && selectedText.endsWith(wrapper)) {
                // Remove wrapper
                val unwrapped = selectedText.removePrefix(wrapper).removeSuffix(wrapper)
                val updatedText = text.replaceRange(selection.start, selection.end, unwrapped)
                updatedText to TextRange(selection.start, selection.start + unwrapped.length)
            } else {
                // Add wrapper
                val wrapped = "$wrapper$selectedText$wrapper"
                val updatedText = text.replaceRange(selection.start, selection.end, wrapped)
                updatedText to TextRange(selection.start, selection.start + wrapped.length)
            }
            
            value.copy(
                text = newText,
                selection = newSelection
            )
        }
    }

    fun prependBulletLine(value: TextFieldValue): TextFieldValue {
        val bullet = "• "
        val text = value.text
        val selection = value.selection
        
        val newText = if (text.isBlank()) {
            bullet
        } else {
            val prefix = if (text.endsWith("\n") || text.isEmpty()) "" else "\n"
            text + prefix + bullet
        }
        
        return value.copy(
            text = newText,
            selection = TextRange(newText.length)
        )
    }
}
