package ir.armin.mindnest

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performTextInput
import ir.armin.mindnest.features.theme.MindNestTheme
import ir.armin.mindnest.features.ui.screens.addNoteScreen.components.NoteEditorFields
import org.junit.Rule
import org.junit.Test

class NoteContentFieldTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    @Test
    fun noteContentField_InitiallyShowsPlaceholder() {
        composeTestRule.setContent {
            MindNestTheme {
                NoteEditorFields(
                    title = "",
                    content = "",
                    onTitleChange = {},
                    onContentChange = {}
                )
            }
        }

        composeTestRule.onNodeWithText("Write your note…").assertIsDisplayed()
    }

    @Test
    fun noteContentField_DisplaysCorrectText() {
        val testText = "this is test"

        composeTestRule.setContent {
            MindNestTheme {
                NoteEditorFields(
                    title = "",
                    content = testText,
                    onTitleChange = {},
                    onContentChange = {}
                )
            }
        }

        composeTestRule.onNodeWithText(testText).assertIsDisplayed()
    }

    @Test
    fun noteContentField_UpdatesTextOnInput() {
        var inputResult = ""

        composeTestRule.setContent {
            MindNestTheme {
                NoteEditorFields(
                    title = "",
                    content = "",
                    onTitleChange = {},
                    onContentChange = { inputResult = it }
                )
            }
        }

        composeTestRule.onNodeWithText("Write your note…").performTextInput("Hello")

        assert(inputResult == "Hello")
    }
}
