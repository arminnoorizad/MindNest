package ir.armin.mindnest.features.ui.screens.addNoteScreen.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.carousel.HorizontalMultiBrowseCarousel
import androidx.compose.material3.carousel.rememberCarouselState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import ir.armin.mindnest.R
import ir.armin.mindnest.data.model.BackgroundOption
import ir.armin.mindnest.utils.BackgroundResources

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NoteBackgroundCarousel(
    allOptions: List<BackgroundOption>,
    selectedResId: Int?,
    onBackgroundSelected: (Int) -> Unit,
    modifier: Modifier = Modifier
) {
    val resolvedSelection = selectedResId ?: BackgroundResources.defaultId
    val initialIndex = allOptions.indexOfFirst { it.resId == resolvedSelection }.coerceAtLeast(0)

    val carouselState = rememberCarouselState(
        initialItem = initialIndex,
        itemCount = { allOptions.size }
    )

    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(vertical = 12.dp)
    ) {
        Text(
            text = stringResource(R.string.background_style),
            style = MaterialTheme.typography.titleMedium,
            modifier = Modifier.padding(start = 20.dp, bottom = 12.dp)
        )

        HorizontalMultiBrowseCarousel(
            state = carouselState,
            preferredItemWidth = 120.dp,
            itemSpacing = 8.dp,
            contentPadding = PaddingValues(horizontal = 20.dp),
            modifier = Modifier
                .fillMaxWidth()
                .height(140.dp)
        ) { page ->
            val option = allOptions[page]

            Card(
                onClick = { onBackgroundSelected(option.resId) },
                modifier = Modifier
                    .testTag("bg_option_${option.resId}")
                    .maskClip(MaterialTheme.shapes.extraLarge)
                    .fillMaxSize(),
                border = if (option.resId == resolvedSelection) {
                    BorderStroke(3.dp, MaterialTheme.colorScheme.primary)
                } else {
                    null
                }
            ) {
                Box(modifier = Modifier.fillMaxSize()) {
                    if (option.resId == BackgroundResources.defaultId) {
                        Box(
                            modifier = Modifier
                                .fillMaxSize()
                                .background(MaterialTheme.colorScheme.surfaceVariant),
                            contentAlignment = Alignment.Center
                        ) {
                            Text(
                                text = stringResource(R.string.default_background),
                                style = MaterialTheme.typography.labelLarge
                            )
                        }
                    } else {
                        Image(
                            painter = painterResource(id = option.resId),
                            contentDescription = null,
                            contentScale = ContentScale.Crop,
                            modifier = Modifier.fillMaxSize()
                        )
                    }
                }
            }
        }
    }
}
