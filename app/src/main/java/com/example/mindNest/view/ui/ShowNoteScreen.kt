package com.example.mindNest.view.ui

import android.annotation.SuppressLint
import android.content.Context
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.detectHorizontalDragGestures
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.FloatingActionButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import coil.compose.AsyncImage
import com.example.mindNest.R
import com.example.mindNest.model.database.NoteEntity
import com.example.mindNest.view.navigation.Destinations
import com.example.mindNest.viewmodel.NoteViewModel

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun ShowNoteScreen(
    navHostController: NavHostController,
    viewModel: NoteViewModel = hiltViewModel()
) {
    var searchQuery by remember { mutableStateOf("") }
    val data by viewModel.allNotes.observeAsState(emptyList())
    val searchResults by viewModel.searchResults.observeAsState(emptyList())

    var showDialog by remember { mutableStateOf(false) }
    var noteToDelete by remember { mutableStateOf<NoteEntity?>(null) }

    MaterialTheme(
        colorScheme = MaterialTheme.colorScheme.copy(primary = Color.Blue)
    ) {
        Scaffold(
            topBar = {
                SearchTopBar(
                    searchQuery = searchQuery,
                    onSearchQueryChange = {
                        searchQuery = it
                        viewModel.searchNotes(it)
                    }
                )
            },
            floatingActionButton = {
                FloatingActionButton(
                    onClick = {
                        navHostController.navigate(Destinations.AddNoteScreen)
                    },
                    modifier = Modifier.padding(end = 16.dp, bottom = 16.dp),
                    shape = FloatingActionButtonDefaults.shape,
                ) {
                    Icon(imageVector = Icons.Default.Add, contentDescription = "Add Note")
                }
            }
        ) { paddingValues ->

            if (data.isEmpty() && searchResults.isEmpty()) {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(paddingValues),
                    contentAlignment = Alignment.Center
                ) {
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Center,
                        modifier = Modifier
                            .fillMaxSize()

                    ) {
                        Image(
                            painter = painterResource(id = R.drawable.empty_background_show_note),
                            contentDescription = "Empty State",
                            modifier = Modifier
                                .height(150.dp)
                                .width(150.dp)
                                .alpha(0.8F)
                                .clip(shape = RoundedCornerShape(10.dp)),
                            contentScale = ContentScale.Crop
                        )
                        Text(
                            text = "No notes available",
                            style = MaterialTheme.typography.bodyMedium,
                            color = MaterialTheme.colorScheme.onBackground
                        )
                    }
                }
            } else {
                LazyVerticalGrid(
                    columns = GridCells.Fixed(2),
                    modifier = Modifier
                        .fillMaxSize() // تغییر از fillMaxWidth به fillMaxSize
                        .padding(top = paddingValues.calculateTopPadding()) // اضافه کردن padding برای Scaffold
                        .padding(horizontal = 8.dp), // کاهش padding افقی
                    contentPadding = PaddingValues(
                        top = 16.dp,
                        bottom = 76.dp // افزایش padding پایین برای FAB
                    ),
                    verticalArrangement = Arrangement.spacedBy(8.dp),
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    val notesToDisplay = if (searchQuery.isEmpty()) data else searchResults
                    items(notesToDisplay) { note ->
                        SwipeToDismissNote(
                            note = note,
                            onDeleteClick = {
                                noteToDelete = note
                                showDialog = true
                            },
                            onNoteClick = { noteId ->
                                navHostController.navigate(Destinations.addNoteScreen(noteId))
                            }
                        )
                    }
                }
            }
        }
    }

    if (showDialog) {
        AlertDialog(
            onDismissRequest = { showDialog = false },
            title = { Text("Delete Note") },
            text = { Text("Are you sure you want to delete this note?") },
            confirmButton = {
                TextButton(
                    onClick = {
                        noteToDelete?.let { viewModel.deleteNote(it) }
                        showDialog = false
                    }
                ) {
                    Text("Yes")
                }
            },
            dismissButton = {
                TextButton(onClick = { showDialog = false }) {
                    Text("No")
                }
            }
        )
    }
}

@SuppressLint("UseOfNonLambdaOffsetOverload")
@Composable
fun SwipeToDismissNote(
    note: NoteEntity,
    onDeleteClick: () -> Unit,
    onNoteClick: (Int) -> Unit
) {
    var dragOffset by remember { mutableFloatStateOf(0f) }
    val animatedOffset by animateDpAsState(targetValue = dragOffset.dp, label = "animated")

    Card(
        modifier = Modifier
            .offset(x = animatedOffset)
            .fillMaxSize()
            .pointerInput(Unit) {
                detectHorizontalDragGestures(

                    onDragEnd = {
                        if (dragOffset > 150) {
                            onDeleteClick()
                        }
                        dragOffset = 0f
                    }
                ) { _, dragAmount ->
                    dragOffset += dragAmount
                }
            }
            .clickable { onNoteClick(note.id) },
        elevation = CardDefaults.cardElevation(4.dp),
        shape = MaterialTheme.shapes.medium,
    ) {
        NoteItem(note = note)
    }
}

@Composable
fun SearchTopBar(
    searchQuery: String,
    onSearchQueryChange: (String) -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
            .padding(top = 32.dp),
        elevation = CardDefaults.cardElevation(
            defaultElevation = 8.dp
        ),
        colors = CardDefaults.cardColors(
            containerColor = Color(0xFFF3E8FF) // Light Lavender
        ),
        shape = MaterialTheme.shapes.large
    ) {
        OutlinedTextField(
            value = searchQuery,
            onValueChange = onSearchQueryChange,
            label = {
                Text(
                    text = "Search Notes",
                    color = Color(0xFF6A4E77) // Dark Lavender
                )
            },
            leadingIcon = {
                Icon(
                    imageVector = Icons.Default.Search,
                    contentDescription = "Search Icon",
                    tint = Color(0xFF6A4E77) // Dark Lavender
                )
            },
            colors = TextFieldDefaults.colors(
                unfocusedLabelColor = Color(0xFF6A4E77),
                focusedLabelColor = Color(0xFF9B59B6),
                cursorColor = Color(0xFF9B59B6),

                ),
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp),
            singleLine = true
        )
    }
}
@Composable
fun NoteItem(note: NoteEntity) {
    Card(
        modifier = Modifier
            .fillMaxSize()
            .height(250.dp), // ارتفاع ثابت برای همه کارت‌ها
        elevation = CardDefaults.cardElevation(4.dp),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(
            containerColor = Color.White.copy(alpha = 0.9f)
        )
    ) {
        Box(
            modifier = Modifier.fillMaxSize()
        ) {
            // پس زمینه
            note.BackgroundImage?.let { backgroundImageRes ->
                Image(
                    painter = painterResource(id = backgroundImageRes),
                    contentDescription = null,
                    modifier = Modifier
                        .fillMaxSize(),
                    contentScale = ContentScale.Crop,
                    alpha = 0.3f // شفافیت پس زمینه
                )
            }

            // محتوای نوت
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp)
            ) {
                // عنوان
                Text(
                    text = note.title,
                    style = MaterialTheme.typography.titleLarge,
                    maxLines = 1,
                    modifier = Modifier.padding(bottom = 8.dp)
                )

                // توضیحات
                Text(
                    text = note.description,
                    style = MaterialTheme.typography.bodyMedium,
                    maxLines = 3,
                    modifier = Modifier
                        .weight(1f)
                        .padding(bottom = 8.dp)
                )

                // فضای خالی برای تنظیم محتوا
                Spacer(modifier = Modifier.weight(1f))

                // ردیف پایین برای نمایش آیکون‌ها و تصویر
                Row(
                    modifier = Modifier
                        .fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.Bottom
                ) {
                    // آیکون صدا
                    if (!note.voice.isNullOrEmpty() && note.voice != "null") {
                        Card(
                            modifier = Modifier.size(36.dp),
                            shape = RoundedCornerShape(8.dp),
                            colors = CardDefaults.cardColors(
                                containerColor = MaterialTheme.colorScheme.primaryContainer
                            )
                        ) {
                            Icon(
                                painter = painterResource(id = R.drawable.add_voice_ic),
                                contentDescription = "Voice Note",
                                modifier = Modifier
                                    .padding(8.dp)
                                    .size(20.dp),
                                tint = MaterialTheme.colorScheme.onPrimaryContainer
                            )
                        }
                    }


                    if (!note.image.isNullOrEmpty() && note.image != "null") {
                        AsyncImage(
                            model = note.image,
                            contentDescription = "Note Image",
                            modifier = Modifier
                                .size(200.dp)
                                .clip(RoundedCornerShape(8.dp)),
                            contentScale = ContentScale.Crop
                        )
                    }
                }
            }
        }
    }
}


 
