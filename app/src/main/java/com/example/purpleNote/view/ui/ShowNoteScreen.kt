package com.example.purpleNote.view.ui

import android.annotation.SuppressLint
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.detectHorizontalDragGestures
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
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
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import coil.compose.AsyncImage
import com.example.purpleNote.R
import com.example.purpleNote.model.database.NoteEntity
import com.example.purpleNote.view.navigation.Destinations
import com.example.purpleNote.viewmodel.NoteViewModel

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
                        .padding(16.dp)
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.empty_background_show_note),
                        contentDescription = "Empty State",
                        modifier = Modifier
                            .height(150.dp)
                            .width(150.dp)
                            .alpha(0.8F)
                            .clip(shape = RoundedCornerShape(10.dp))
                            .padding(bottom = 16.dp)
                            .shadow(shape = RoundedCornerShape(10.dp), elevation = 8.dp),
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
                    .fillMaxWidth() // استفاده از عرض کامل
                    .padding(horizontal = 16.dp) // پدینگ یکسان از طرفین
                    .padding(top = 120.dp), // فاصله از بالا
                contentPadding = PaddingValues(
                    top = 16.dp,
                    bottom = 16.dp
                ), // فاصله از بالا و پایین
                verticalArrangement = Arrangement.spacedBy(12.dp), // فاصله عمودی بین آیتم‌ها
                horizontalArrangement = Arrangement.spacedBy(12.dp) // فاصله افقی بین آیتم‌ها
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
    var dragOffset by remember { mutableStateOf(0f) }
    val animatedOffset by animateDpAsState(targetValue = dragOffset.dp)

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
            .clickable { onNoteClick(note.id) }, // اضافه کردن کلیکیبل با آیدی نوت
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
            defaultElevation = 10.dp
        ),
        shape = MaterialTheme.shapes.medium
    ) {
        OutlinedTextField(
            value = searchQuery,
            onValueChange = onSearchQueryChange,
            label = { Text("Search Notes") },
            leadingIcon = {
                Icon(
                    imageVector = Icons.Default.Search,
                    contentDescription = "Search Icon",
                    tint = MaterialTheme.colorScheme.onSurface
                )
            },
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp),
            singleLine = true
        )
    }
}


@Composable
fun NoteItem(note: NoteEntity) {
    Column(modifier = Modifier.padding(16.dp)) {
        Text(
            text = note.title,
            style = MaterialTheme.typography.headlineMedium,
            modifier = Modifier.padding(bottom = 4.dp)
        )
        Text(
            text = note.description,
            style = MaterialTheme.typography.bodyMedium,
            modifier = Modifier.padding(bottom = 8.dp)
        )

        note.image?.let { imagePath ->
            AsyncImage(
                model = imagePath,
                contentDescription = "Note Image",
                modifier = Modifier
                    .fillMaxWidth()
                    .height(150.dp)
                    .clip(RoundedCornerShape(8.dp)),
                contentScale = ContentScale.Crop
            )
        }
        note.voice.let {voicePath->
            if (voicePath != null) {
                androidx.compose.material3.Icon(painter = painterResource(id = R.drawable.add_voice_ic), tint = Color.Cyan, contentDescription = "")


            }
        }
    }
}

