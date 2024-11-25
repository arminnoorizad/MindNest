package com.example.mindNest.view.ui

import android.content.Context
import android.content.Intent
import android.media.MediaPlayer
import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.togetherWith
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CutCornerShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import coil.compose.AsyncImage
import com.example.mindNest.R
import com.example.mindNest.view.theme.NotePadTheme
import com.example.mindNest.viewmodel.NoteViewModel


@Composable
fun AddNoteScreen(
    navHostController: NavHostController,
    noteId: Int? = null,
    viewModel: NoteViewModel = hiltViewModel()

) {
    NotePadTheme {


        val scrollState = rememberScrollState()
        var title by remember { mutableStateOf("") }
        var description by remember { mutableStateOf("") }
        var selectedBackgroundImage by remember { mutableStateOf<Int?>(null) }
        var isBottomSheetVisible by remember { mutableStateOf(false) }
        var selectedAudioUri by remember { mutableStateOf<Uri?>(null) }
        var selectedImageUri by remember { mutableStateOf<Uri?>(null) }




        LaunchedEffect(noteId) {
            noteId?.let {
                viewModel.getNoteById(it)
            }
        }

        val note = viewModel.note.value

        LaunchedEffect(note) {
            note?.let { note ->
                title = note.title
                description = note.description
                selectedImageUri = if (!note.image.isNullOrEmpty() && note.image != "null") {
                    Uri.parse(note.image)
                } else {
                    null
                }
                selectedAudioUri = if (!note.voice.isNullOrEmpty() && note.voice != "null") {
                    Uri.parse(note.voice)
                } else {
                    null
                }
                selectedBackgroundImage = note.BackgroundImage
            }
        }

// نمایش پس‌زمینه در UI با استفاده از painterResource برای ID تصویر
        selectedBackgroundImage?.let { imageResId ->
            Image(
                painter = painterResource(id = imageResId),
                contentDescription = "Background Image",
                modifier = Modifier.fillMaxSize(),
                contentScale = ContentScale.Crop
            )
        }
        val galleryLauncher = rememberLauncherForActivityResult(
            contract = ActivityResultContracts.PickVisualMedia(),
            onResult = { uri: Uri? ->
                selectedImageUri = uri
            }
        )
        val audioPickerLauncher = rememberLauncherForActivityResult(
            contract = ActivityResultContracts.StartActivityForResult(),
            onResult = { result ->
                result.data?.data?.let { uri ->
                    selectedAudioUri = uri
                }
            }
        )

        Scaffold(
            topBar = { TopBar(onThemeClick = { isBottomSheetVisible = true }, navHostController) },
            floatingActionButton = {
                val context = LocalContext.current
                FloatingActionButton(
                    onClick = {
                        if (title.isNotEmpty()) {
                            if (noteId == null) {
                                viewModel.upsertNote(
                                    title = title,
                                    description = description,
                                    date = "",
                                    voiceUri = selectedAudioUri.toString(),
                                    category = "",
                                    image = selectedImageUri?.let {
                                        saveImageToInternalStorage(
                                            context,
                                            it
                                        )


                                    },
                                    backgroundImage = selectedBackgroundImage

                                )

                            } else {
                                viewModel.upsertNote(
                                    noteId = noteId,
                                    title = title,
                                    description = description,
                                    date = "",
                                    voiceUri = selectedAudioUri.toString(),
                                    category = "",
                                    image = selectedImageUri.toString(),
                                    backgroundImage = selectedBackgroundImage


                                )
                            }
                            navHostController.navigate("ShowNoteScreen")
                        }
                    }
                ) {
                    Icon(
                        painter = painterResource(id = R.drawable.save_note_ic),
                        modifier = Modifier.size(24.dp),
                        contentDescription = ""
                    )
                }
            },
            bottomBar = {
                CustomBottomAppBar(onImageClick = {
                    galleryLauncher.launch(
                        PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly)
                    )
                }, onVoiceClick = {
                    val intent = Intent(Intent.ACTION_GET_CONTENT).apply {
                        type = "audio/*"
                    }
                    audioPickerLauncher.launch(intent)
                })
            }
        ) { paddingValues ->
            Box(modifier = Modifier.fillMaxSize()) {
                selectedBackgroundImage?.let { imageRes ->

                    AnimatedContent(
                        targetState = imageRes,
                        transitionSpec = {
                            fadeIn(animationSpec = tween(3000)) togetherWith fadeOut(
                                animationSpec = tween(500)
                            )
                        }, label = ""
                    ) { targetImage ->
                        Image(
                            painter = painterResource(id = targetImage),
                            contentDescription = null,
                            contentScale = ContentScale.Crop,
                            modifier = Modifier.fillMaxSize(), alpha = 0.5F
                        )
                    }
                }
            }
            Column(
                modifier = Modifier
                    .padding(paddingValues)
                    .verticalScroll(scrollState)
            ) {
                Spacer(modifier = Modifier.size(16.dp))
                TextTitle(title) { title = it }
                TextDescription(description) { description = it }

                if (isBottomSheetVisible) {
                    ImageSelectionBottomSheet(
                        imageResources = ImageList.availableImages,
                        onImageSelected = { selectedBackgroundImage = it },
                        onDismissRequest = { isBottomSheetVisible = false }
                    )
                }
                if (selectedImageUri != null) {
                    DisplayImage(
                        selectedImageUri = selectedImageUri,
                        onClick = { selectedImageUri = null }
                    )
                }
                selectedAudioUri?.let {
                    AudioPlayerCard(
                        it,
                        onDelete = { selectedAudioUri = null })
                }
            }
        }
    }
}

@Composable
fun DisplayImage(selectedImageUri: Uri?, onClick: () -> Unit) {
    if (selectedImageUri != null) {
        Card(
            modifier = Modifier
                .size(400.dp)
                .padding(horizontal = 24.dp, vertical = 16.dp),
            shape = RoundedCornerShape(20.dp),
        ) {
            Box(
                modifier = Modifier
                    .fillMaxSize() // Ensures the Box fills the Card's area
            ) {
                AsyncImage(
                    model = selectedImageUri,
                    contentDescription = null,
                    modifier = Modifier
                        .fillMaxSize(),
                    contentScale = ContentScale.Crop
                )

                IconButton(
                    onClick = onClick,
                    modifier = Modifier
                        .align(Alignment.TopEnd) // Align icon to the top-end (top-right corner)
                        .padding(8.dp) // Add some padding to keep it away from the edge
                        .zIndex(1f) // Ensure icon is above the image
                ) {
                    Icon(
                        painter = painterResource(id = R.drawable.delete_btn),
                        contentDescription = null,
                        modifier = Modifier.size(24.dp)
                    )
                }
            }
        }
    }
}
fun saveImageToInternalStorage(context: Context, uri: Uri): String {
    val fileName = "image_${System.currentTimeMillis()}.jpg" // Unique file name
    val outputStream = context.openFileOutput(fileName, Context.MODE_PRIVATE)
    val inputStream = context.contentResolver.openInputStream(uri)

    inputStream?.use { input ->
        outputStream.use { output ->
            input.copyTo(output)
        }
    }
    return "${context.filesDir}/$fileName" // Return the file path as a String
}


@Composable
fun AudioPlayerCard(audioUri: Uri, onDelete: () -> Unit) {
    val context = LocalContext.current
    var mediaPlayer by remember { mutableStateOf<MediaPlayer?>(null) }
    var isPlaying by remember { mutableStateOf(false) }

    LaunchedEffect(audioUri) {
        mediaPlayer?.release()
        mediaPlayer = MediaPlayer.create(context, audioUri)
    }

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(24.dp),
        shape = CutCornerShape(topEnd = 24.dp),
        colors = CardDefaults.cardColors(
            containerColor = Color.White.copy(0.3F)
        )

    ) {

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceEvenly,
            verticalAlignment = Alignment.CenterVertically
        ) {
            IconButton(onClick = {
                if (!isPlaying) {
                    mediaPlayer?.start()
                    isPlaying = true
                }
            }, enabled = !isPlaying) {
                Icon(painter = painterResource(id = R.drawable.play_icon), contentDescription = "")



            }


            IconButton(onClick = {
                mediaPlayer?.pause()
                isPlaying = false
            }, enabled = isPlaying) {
                Icon(painter = painterResource(id = R.drawable.pause_btn), contentDescription = "")
            }


            IconButton(onClick = {
                mediaPlayer?.release()
                mediaPlayer = null
                isPlaying = false
                onDelete()
            }) {

                Icon(painter = painterResource(id = R.drawable.delete_btn), contentDescription = "")


            }
        }

        DisposableEffect(Unit) {
            onDispose {
                mediaPlayer?.release()
            }
        }
    }
}


@Preview(
    showSystemUi = true,
    showBackground = true
)
@Composable
fun PreviewAddNoteScreen() {


}
