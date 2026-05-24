package ir.armin.mindnest.features.ui.screens.addNoteScreen.util

import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.input.OffsetMapping
import androidx.compose.ui.text.input.TransformedText
import androidx.compose.ui.text.input.VisualTransformation

class MarkdownVisualTransformation : VisualTransformation {
    override fun filter(text: AnnotatedString): TransformedText {
        val result = MarkdownMapper.parseMarkdown(text.text)
        
        val offsetMapping = object : OffsetMapping {
            override fun originalToTransformed(offset: Int): Int {
                val index = result.offsetMap.indexOfFirst { it >= offset }
                return if (index != -1) index else result.annotatedString.length
            }

            override fun transformedToOriginal(offset: Int): Int {
                return if (offset in result.offsetMap.indices) {
                    result.offsetMap[offset]
                } else {
                    result.offsetMap.lastOrNull() ?: 0
                }
            }
        }

        return TransformedText(result.annotatedString, offsetMapping)
    }
}
