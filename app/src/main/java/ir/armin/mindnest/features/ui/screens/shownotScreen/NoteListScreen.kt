package ir.armin.mindnest.features.ui.screens.shownotScreen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import ir.armin.mindnest.features.theme.FabContainer
import ir.armin.mindnest.features.theme.FabContent
import ir.armin.mindnest.features.theme.TopBarColor

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NoteListScreen(
    viewModel: NoteListViewModel = hiltViewModel(),
    onAddNewNote: () -> Unit,
    onEditNote: (Int) -> Unit
) {
    val state by viewModel.state.collectAsState()
    val snackBarHostState = remember { SnackbarHostState() }

    NoteListSideEffects(
        state = state,
        viewModel = viewModel,
        snackBarHostState = snackBarHostState
    )

    if (state.noteToDeleteId != null) {
        DeleteNoteDialog(
            onConfirm = { viewModel.deleteNote(state.noteToDeleteId!!) },
            onDismiss = { viewModel.onDismissDeleteDialog() }
        )
    }

    Scaffold(
        snackbarHost = { SnackbarHost(snackBarHostState) },
        topBar = {
            CenterAlignedTopAppBar(
                title = { Text(text = "My Notes") },
                colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
                    containerColor = TopBarColor
                )
            )
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = onAddNewNote,
                containerColor = FabContainer,
                contentColor = FabContent
            ) {
                Icon(Icons.Filled.Add, contentDescription = "Add note")
            }
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .padding(paddingValues)
                .fillMaxSize()
                .background(MaterialTheme.colorScheme.background)
        ) {
            SearchAndHeaderSection(
                query = state.searchQuery,
                onQueryChange = viewModel::setSearchQuery
            )

            if (state.isLoading || state.isDeletionInProgress) {
                LinearProgressIndicator(modifier = Modifier.fillMaxWidth())
            }

            if (state.notes.isEmpty() && !state.isLoading) {
                EmptyState(state = state)
            } else {
                NoteListContent(
                    notes = state.notes,
                    onNoteClick = onEditNote,
                    onDelete = viewModel::onSwipeToDelete
                )
            }
        }
    }
}
