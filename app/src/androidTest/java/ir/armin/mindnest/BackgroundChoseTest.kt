package ir.armin.mindnest

import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.performClick
import ir.armin.mindnest.data.model.BackgroundOption
import ir.armin.mindnest.features.ui.screens.addNoteScreen.components.NoteBackgroundCarousel
import org.junit.Rule
import org.junit.Test

class BackgroundChoseTest {

    @get:Rule
    val composeTestRule = createComposeRule()


    @Test
    fun whenOptionClicked_callbackIsInvokedWithCorrectId() {
        val resId1 = android.R.drawable.ic_menu_report_image
        val resId2 = android.R.drawable.ic_menu_gallery


        val options = listOf(
            BackgroundOption(resId = resId1),
            BackgroundOption(resId = resId2)
        )
        var capturedId = -1

        composeTestRule.setContent {
            NoteBackgroundCarousel(
                allOptions = options,
                selectedResId = resId1,
                onBackgroundSelected = { capturedId = it }
            )
        }

        composeTestRule.onNodeWithTag("bg_option_$resId2").performClick()

        assert(capturedId == resId2)
    }
}