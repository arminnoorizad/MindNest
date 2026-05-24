package ir.armin.mindnest.features.ui.screens.addNoteScreen.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import ir.armin.mindnest.R
import ir.armin.mindnest.features.ui.screens.addNoteScreen.util.MarkdownVisualTransformation

@Composable
fun NoteEditorFields(
    title: TextFieldValue,
    content: TextFieldValue,
    onTitleChange: (TextFieldValue) -> Unit,
    onContentChange: (TextFieldValue) -> Unit,
    modifier: Modifier = Modifier
) {
    val markdownTransformation = remember { MarkdownVisualTransformation() }
    
    val titleStyle = TextStyle(
        fontSize = 28.sp,
        fontWeight = FontWeight.Bold,
        letterSpacing = 2.sp,
        color = MaterialTheme.colorScheme.onSurface,
        lineHeight = 34.sp
    )
    val bodyStyle = MaterialTheme.typography.bodyLarge.copy(
        fontSize = 17.sp,
        lineHeight = 26.sp,
        letterSpacing = 2.sp,
        color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.85f)
    )

    Column(modifier = modifier.fillMaxWidth()) {
        BasicTextField(
            value = title,
            onValueChange = onTitleChange,
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 24.dp)
                .padding(vertical = 24.dp)
                .testTag("note_title_input"),
            textStyle = titleStyle,
            singleLine = true,
            cursorBrush = SolidColor(MaterialTheme.colorScheme.primary),
            decorationBox = { inner ->
                if (title.text.isEmpty()) {
                    Text(
                        text = stringResource(R.string.note_title_hint),
                        style = titleStyle.copy(
                            color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.38f)
                        )
                    )
                }
                inner()
            }
        )

        BasicTextField(
            value = content,
            onValueChange = onContentChange,
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 20.dp, vertical = 24.dp)
                .heightIn(min = 110.dp)
                .testTag("note_content_input"),
            textStyle = bodyStyle,
            cursorBrush = SolidColor(MaterialTheme.colorScheme.primary),
            visualTransformation = markdownTransformation,
            decorationBox = { inner ->
                if (content.text.isEmpty()) {
                    Text(
                        text = stringResource(R.string.note_content_hint),
                        style = bodyStyle.copy(
                            color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.38f)
                        )
                    )
                }
                inner()
            }
        )
    }
}
