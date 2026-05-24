package ir.armin.mindnest.features.ui.screens.addNoteScreen

import android.Manifest
import android.content.pm.PackageManager
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.core.content.ContextCompat
import androidx.hilt.navigation.compose.hiltViewModel
import ir.armin.mindnest.features.ui.screens.addNoteScreen.components.AddEditBottomToolbar
import ir.armin.mindnest.features.ui.screens.addNoteScreen.components.AddEditSaveFab
import ir.armin.mindnest.features.ui.screens.addNoteScreen.components.AddEditTopBar
import ir.armin.mindnest.features.ui.screens.addNoteScreen.components.NoteBackgroundLayer
import ir.armin.mindnest.features.ui.screens.addNoteScreen.components.NoteEditorContent
import ir.armin.mindnest.utils.BackgroundResources
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddEditNoteScreen(
    viewModel: AddEditNoteViewModel = hiltViewModel(),
    onNavigateBack: () -> Unit
) {
    val state by viewModel.state.collectAsState()
    val context = LocalContext.current
    val scope = rememberCoroutineScope()
    val scrollState = rememberScrollState()
    val snackBarHostState = remember { SnackbarHostState() }

    val displayImage: Any? = state.tempImageUri ?: state.imagePath
    val backgroundResId = state.backgroundImageResId ?: BackgroundResources.defaultId
    var showBackgroundSelector by remember { mutableStateOf(false) }

    LaunchedEffect(state.isNoteSaved) {
        if (state.isNoteSaved) onNavigateBack()
    }

    LaunchedEffect(state.error) {
        state.error?.let { message ->
            snackBarHostState.showSnackbar(message = message, actionLabel = "Dismiss")
            viewModel.clearError()
        }
    }

    val imagePickerLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent(),
        onResult = viewModel::onImageSelected
    )

    val audioPermissionLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestPermission(),
        onResult = { granted ->
            if (granted) {
                viewModel.toggleRecording()
            } else {
                scope.launch {
                    snackBarHostState.showSnackbar(
                        message = "Audio recording permission denied",
                        actionLabel = "OK"
                    )
                }
            }
        }
    )

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        snackbarHost = { SnackbarHost(snackBarHostState) },
        topBar = {
            AddEditTopBar(
                onBackClick = onNavigateBack,
                onChangeBackgroundClick = {
                    showBackgroundSelector = !showBackgroundSelector
                    scope.launch {
                        scrollState.animateScrollTo(scrollState.maxValue)
                    }
                }
            )
        },
        bottomBar = {
            AddEditBottomToolbar(
                isRecording = state.isRecording,
                onBoldClick = viewModel::applyBoldFormatting,
                onItalicClick = viewModel::applyItalicFormatting,
                onListClick = viewModel::applyBulletList,
                onImagePickClick = { imagePickerLauncher.launch("image/*") },
                onAudioClick = {
                    val granted = ContextCompat.checkSelfPermission(
                        context,
                        Manifest.permission.RECORD_AUDIO
                    ) == PackageManager.PERMISSION_GRANTED
                    if (granted) {
                        viewModel.toggleRecording()
                    } else {
                        audioPermissionLauncher.launch(Manifest.permission.RECORD_AUDIO)
                    }
                }
            )
        }
    ) { paddingValues ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
        ) {
            NoteBackgroundLayer(backgroundResId = backgroundResId)


            NoteEditorContent(
                state = state,
                displayImage = displayImage,
                backgroundOptions = BackgroundResources.optionsWithDefault,
                scrollState = scrollState,
                onTitleChange = viewModel::onTitleChange,
                onContentChange = viewModel::onContentChange,
                onBackgroundSelected = viewModel::onBackgroundSelected,
                onVoiceTogglePlayback = viewModel::togglePlayback,
                onVoiceDelete = viewModel::onVoiceDelete,
                onImageDelete = viewModel::onImageDelete,
                showBackgroundSelector = showBackgroundSelector
            )
            AddEditSaveFab(
                onSaveClick = viewModel::saveNote,
                enabled = !state.isLoading,
                modifier = Modifier.align(Alignment.BottomEnd)
            )

            if (state.isLoading) {
                CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
            }
        }
    }
}
