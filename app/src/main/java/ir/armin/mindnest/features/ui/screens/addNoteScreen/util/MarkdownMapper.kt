package ir.armin.mindnest.features.ui.screens.addNoteScreen.util

import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight

data class MarkdownResult(
    val annotatedString: AnnotatedString,
    val offsetMap: List<Int>
)

object MarkdownMapper {
    fun parseMarkdown(text: String): MarkdownResult {
        val offsetMap = mutableListOf<Int>()
        val annotated = buildAnnotatedString {
            var i = 0
            while (i < text.length) {
                when {
                    text.startsWith("**", i) -> {
                        val end = text.indexOf("**", i + 2)
                        if (end != -1) {
                            val content = text.substring(i + 2, end)
                            pushStyle(SpanStyle(fontWeight = FontWeight.Bold))
                            content.forEachIndexed { index, _ ->
                                offsetMap.add(i + 2 + index)
                                append(content[index])
                            }
                            pop()
                            i = end + 2
                        } else {
                            offsetMap.add(i)
                            append(text[i])
                            i++
                        }
                    }
                    text.startsWith("_", i) -> {
                        val end = text.indexOf("_", i + 1)
                        if (end != -1) {
                            val content = text.substring(i + 1, end)
                            pushStyle(SpanStyle(fontStyle = FontStyle.Italic))
                            content.forEachIndexed { index, _ ->
                                offsetMap.add(i + 1 + index)
                                append(content[index])
                            }
                            pop()
                            i = end + 1
                        } else {
                            offsetMap.add(i)
                            append(text[i])
                            i++
                        }
                    }
                    else -> {
                        offsetMap.add(i)
                        append(text[i])
                        i++
                    }
                }
            }
            // انتهای متن
            offsetMap.add(text.length)
        }
        return MarkdownResult(annotated, offsetMap)
    }

    fun toAnnotatedString(text: String): AnnotatedString = parseMarkdown(text).annotatedString
}
