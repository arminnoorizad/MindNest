package ir.armin.mindnest.features.ui.screens.shownotScreen

import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import ir.armin.mindnest.data.model.NoteListState

@Composable
fun NoteListSideEffects(
    state: NoteListState,
    viewModel: NoteListViewModel,
    snackBarHostState: SnackbarHostState
) {
    LaunchedEffect(state.deletionSuccessMessage) {
        state.deletionSuccessMessage?.let { message ->
            snackBarHostState.showSnackbar(message = message)
            viewModel.deletionMessageShown()

        }
    }


    LaunchedEffect(state.error) {
        state.error?.let { errorMessage ->
            snackBarHostState.showSnackbar(
                message = errorMessage,
                actionLabel = "close"
            )
            viewModel.errorHandled()
        }
    }
}