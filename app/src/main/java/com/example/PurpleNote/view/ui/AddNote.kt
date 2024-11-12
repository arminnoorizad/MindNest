package com.example.PurpleNote.view.ui

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
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CutCornerShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
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
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.zIndex
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import coil.compose.AsyncImage
import com.example.PurpleNote.R
import com.example.PurpleNote.viewmodel.NoteViewModel


@Composable
fun AddNoteScreen(
    navHostController: NavHostController,
    noteId: Int? = null,
    viewModel: NoteViewModel = hiltViewModel()

) {
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
        note?.let {
            title = it.title
            description = it.description
            selectedImageUri = it.image?.let { Uri.parse(it) }
            selectedAudioUri = it.voice?.let { Uri.parse(it) }

        }
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

    val availableImages = listOf(
        R.drawable.image_background_4,
        R.drawable.image_background_5,
        R.drawable.image_background_3
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
                                }
                            )

                        } else {
                            viewModel.upsertNote(
                                noteId = noteId,
                                title = title,
                                description = description,
                                date = "",
                                voiceUri = selectedAudioUri.let { audio ->
                                    audio
                                }.toString(),
                                category = "",
                                image = selectedImageUri.let {
                                    it
                                }.toString()
                            )
                        }
                        navHostController.navigate("ShowNoteScreen")
                    }
                }, modifier = Modifier.padding(16.dp)
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
                        fadeIn(animationSpec = tween(5000)) togetherWith fadeOut(
                            animationSpec = tween(500)
                        )
                    }, label = ""
                ) { targetImage ->
                    Image(
                        painter = painterResource(id = targetImage),
                        contentDescription = null,
                        contentScale = ContentScale.Crop,
                        modifier = Modifier.fillMaxSize()
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
                    imageResources = availableImages,
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
            selectedAudioUri?.let { AudioPlayerCard(it, onDelete = { selectedAudioUri = null }) }
        }
    }
}

@Composable
fun TextTitle(title: String, onTitleChange: (String) -> Unit) {
    var errorMessage by remember { mutableStateOf("") }

    // بررسی خالی بودن عنوان
    val isTitleEmpty = title.isEmpty()
    errorMessage = if (isTitleEmpty) {
        "Title can't be empty"
    } else {
        ""
    }

    Column(modifier = Modifier.fillMaxWidth()) {
        TextField(
            value = title,
            onValueChange = onTitleChange,
            label = { Text(text = "Title", color = Color.Gray) },
            shape = CutCornerShape(topEnd = 16.dp),
            textStyle = TextStyle(
                fontSize = 22.sp,
                fontWeight = FontWeight.Bold
            ),
            colors = TextFieldDefaults.colors(
                unfocusedContainerColor = Color.Transparent,
                focusedContainerColor = Color.White
            ),
            modifier = Modifier
                .fillMaxWidth()
                .padding(24.dp),
            isError = isTitleEmpty
        )

        // نمایش پیغام خطا در صورتی که عنوان خالی باشد
        if (errorMessage.isNotEmpty()) {
            Text(
                text = errorMessage,
                color = Color.Red,
                style = TextStyle(fontSize = 14.sp),
                modifier = Modifier.padding(start = 24.dp)
            )
        }
    }
}

@Composable
fun TextDescription(description: String, onDescriptionChange: (String) -> Unit) {
    TextField(
        value = description,
        onValueChange = onDescriptionChange,
        label = { Text(text = "Description", color = Color.Gray) },
        shape = CutCornerShape(topEnd = 16.dp),
        textStyle = TextStyle(
            color = Color.Black,
            fontSize = 18.sp,
            fontWeight = FontWeight.Normal,
        ),
        colors = TextFieldDefaults.colors(
            unfocusedContainerColor = Color.Transparent,
            focusedContainerColor = Color.White
        ),
        modifier = Modifier
            .fillMaxWidth()
            .padding(24.dp)
    )
}


@Composable
fun TopBar(onThemeClick: () -> Unit, navHostController: NavHostController) {


    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 30.dp, start = 24.dp, end = 24.dp)
            .background(Color.White.copy(0.1F)),
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
                    modifier = Modifier.size(24.dp) // تغییر اندازه آیکون
                )
            }

            IconButton(onClick = onVoiceClick) {
                Icon(
                    painter = painterResource(id = R.drawable.add_voice_ic),
                    contentDescription = "Record Voice",
                    modifier = Modifier.size(24.dp) // تغییر اندازه آیکون
                )
            }
        }
    }
}


@Composable
fun DisplayImage(selectedImageUri: Uri?, onClick: () -> Unit) {
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
            // Display the image
            AsyncImage(
                model = selectedImageUri,
                contentDescription = null,
                modifier = Modifier
                    .fillMaxSize(),
                contentScale = ContentScale.Crop
            )

            // Display delete icon at the top-right corner
            IconButton(
                onClick = onClick,
                modifier = Modifier
                    .align(Alignment.TopEnd) // Align icon to the top-end (top-right corner)
                    .padding(8.dp) // Add some padding to keep it away from the edge
                    .zIndex(1f) // Ensure icon is above the image
            ) {
                Icon(imageVector = Icons.Default.Delete, contentDescription = null)
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
        shape = CutCornerShape(24.dp), colors = CardDefaults.cardColors(
            containerColor = Color.Transparent
        )

    ) {

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceEvenly,
            verticalAlignment = Alignment.CenterVertically
        ) {
            // دکمه پخش
            IconButton(onClick = {
                if (!isPlaying) {
                    mediaPlayer?.start()
                    isPlaying = true
                }
            }, enabled = !isPlaying) {
                Icon(
                    imageVector = Icons.Default.PlayArrow,
                    contentDescription = "پخش",
                    tint = if (isPlaying) Color.Gray else Color.Black
                )
            }

            // دکمه توقف
            IconButton(onClick = {
                mediaPlayer?.pause()
                isPlaying = false
            }, enabled = isPlaying) {
                Icon(
                    imageVector = Icons.AutoMirrored.Filled.Send,
                    contentDescription = "توقف",
                    tint = if (isPlaying) Color.Black else Color.Gray
                )
            }


            IconButton(onClick = {
                mediaPlayer?.release()
                mediaPlayer = null
                isPlaying = false
                onDelete()
            }) {
                Icon(
                    imageVector = Icons.Default.Delete,
                    contentDescription = "حذف",
                    tint = Color.Red
                )
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
