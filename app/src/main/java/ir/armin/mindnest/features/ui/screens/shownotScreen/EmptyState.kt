package ir.armin.mindnest.features.ui.screens.shownotScreen

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import ir.armin.mindnest.R
import ir.armin.mindnest.data.model.NoteListState

@Composable
fun EmptyState(state: NoteListState) {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {


            Image(painter = painterResource(R.drawable.empty_back ), "" ,
                contentScale = ContentScale.Crop ,
                modifier = Modifier.size(200.dp))

            Spacer(modifier = Modifier.height(16.dp))

            Text(
                text = if (state.searchQuery.isNotEmpty()) {
                    "No notes found for \"${state.searchQuery}\""
                } else {
                    "Your MindNest is empty! Tap the + button to capture an idea."
                },
                style = MaterialTheme.typography.titleMedium,
                color = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.7f),
                textAlign = TextAlign.Center,
                modifier = Modifier.padding(horizontal = 32.dp)
            )
        }
    }
}

