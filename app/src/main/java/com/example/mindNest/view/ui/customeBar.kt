package com.example.mindNest.view.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.mindNest.R

@Composable
fun TopBar(onThemeClick: () -> Unit, navHostController: NavHostController) {

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 30.dp, start = 24.dp, end = 24.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically,


        ) {
        IconButton(onClick = { navHostController.navigate("ShowNoteScreen") }) {
            Icon(
                painter = painterResource(id = R.drawable.arow_back_ic),
                contentDescription = "",
                modifier = Modifier.size(32.dp)
            )
        }
        IconButton(onClick = onThemeClick) {
            Icon(
                painter = painterResource(id = R.drawable.change_background_ic),
                contentDescription = "",
                modifier = Modifier.size(32.dp)
            )
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CustomBottomAppBar(
    onImageClick: () -> Unit,
    onVoiceClick: () -> Unit
) {
    BottomAppBar(
        modifier = Modifier

            .fillMaxWidth(), containerColor = Color.White.copy(0.2F)


    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceEvenly,
            verticalAlignment = Alignment.CenterVertically
        ) {

            IconButton(onClick = onImageClick) {
                Icon(
                    painter = painterResource(id = R.drawable.add_image_ic),
                    contentDescription = "Select Image",
                    modifier = Modifier.size(24.dp)
                )
            }

            IconButton(onClick = onVoiceClick) {
                Icon(
                    painter = painterResource(id = R.drawable.add_voice_ic),
                    contentDescription = "Record Voice",
                    modifier = Modifier.size(24.dp)
                )
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ImageSelectionBottomSheet(
    imageResources: List<Int>,
    onImageSelected: (Int) -> Unit,
    onDismissRequest: () -> Unit
) {
    ModalBottomSheet(onDismissRequest = onDismissRequest) {
        LazyRow(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            items(imageResources.size) { index ->
                val imageRes = imageResources[index]
                Card(
                    modifier = Modifier
                        .size(100.dp)
                        .clickable {
                            onImageSelected(imageRes)
                            onDismissRequest()
                        },
                    shape = RoundedCornerShape(16.dp),
                    elevation = CardDefaults.elevatedCardElevation()
                ) {
                    Image(
                        painter = painterResource(id = imageRes),
                        contentDescription = "Image $index",
                        contentScale = ContentScale.Crop,
                        modifier = Modifier.fillMaxSize()
                    )
                }
            }
        }
    }
}

