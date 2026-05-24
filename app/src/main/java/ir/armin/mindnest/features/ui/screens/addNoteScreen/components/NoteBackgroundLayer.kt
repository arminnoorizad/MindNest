package ir.armin.mindnest.features.ui.screens.addNoteScreen.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import ir.armin.mindnest.utils.BackgroundResources

@Composable
fun NoteBackgroundLayer(
    backgroundResId: Int,
    modifier: Modifier = Modifier
) {
    if (backgroundResId == BackgroundResources.defaultId) {
        Box(
            modifier = modifier
                .fillMaxSize()
                .background(MaterialTheme.colorScheme.background)
        )
    } else {
        Image(
            painter = painterResource(id = backgroundResId),
            contentDescription = null,
            modifier = modifier
                .fillMaxSize()
                .alpha(0.35f),
            contentScale = ContentScale.Crop
        )
        Box(
            modifier = modifier
                .fillMaxSize()
                .background(MaterialTheme.colorScheme.surface.copy(alpha = 0.55f))
        )
    }
}
