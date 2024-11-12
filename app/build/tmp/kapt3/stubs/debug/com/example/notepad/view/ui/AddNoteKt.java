package com.example.notepad.view.ui;

@kotlin.Metadata(mv = {1, 9, 0}, k = 2, xi = 48, d1 = {"\u0000@\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\b\n\u0002\u0010 \n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0010\u000e\n\u0002\b\u0007\u001a+\u0010\u0000\u001a\u00020\u00012\u0006\u0010\u0002\u001a\u00020\u00032\n\b\u0002\u0010\u0004\u001a\u0004\u0018\u00010\u00052\b\b\u0002\u0010\u0006\u001a\u00020\u0007H\u0007\u00a2\u0006\u0002\u0010\b\u001a\u001e\u0010\t\u001a\u00020\u00012\u0006\u0010\n\u001a\u00020\u000b2\f\u0010\f\u001a\b\u0012\u0004\u0012\u00020\u00010\rH\u0007\u001a$\u0010\u000e\u001a\u00020\u00012\f\u0010\u000f\u001a\b\u0012\u0004\u0012\u00020\u00010\r2\f\u0010\u0010\u001a\b\u0012\u0004\u0012\u00020\u00010\rH\u0007\u001a \u0010\u0011\u001a\u00020\u00012\b\u0010\u0012\u001a\u0004\u0018\u00010\u000b2\f\u0010\u0013\u001a\b\u0012\u0004\u0012\u00020\u00010\rH\u0007\u001a8\u0010\u0014\u001a\u00020\u00012\f\u0010\u0015\u001a\b\u0012\u0004\u0012\u00020\u00050\u00162\u0012\u0010\u0017\u001a\u000e\u0012\u0004\u0012\u00020\u0005\u0012\u0004\u0012\u00020\u00010\u00182\f\u0010\u0019\u001a\b\u0012\u0004\u0012\u00020\u00010\rH\u0007\u001a\b\u0010\u001a\u001a\u00020\u0001H\u0007\u001a$\u0010\u001b\u001a\u00020\u00012\u0006\u0010\u001c\u001a\u00020\u001d2\u0012\u0010\u001e\u001a\u000e\u0012\u0004\u0012\u00020\u001d\u0012\u0004\u0012\u00020\u00010\u0018H\u0007\u001a$\u0010\u001f\u001a\u00020\u00012\u0006\u0010 \u001a\u00020\u001d2\u0012\u0010!\u001a\u000e\u0012\u0004\u0012\u00020\u001d\u0012\u0004\u0012\u00020\u00010\u0018H\u0007\u001a\u001e\u0010\"\u001a\u00020\u00012\f\u0010#\u001a\b\u0012\u0004\u0012\u00020\u00010\r2\u0006\u0010\u0002\u001a\u00020\u0003H\u0007\u00a8\u0006$"}, d2 = {"AddNoteScreen", "", "navHostController", "Landroidx/navigation/NavHostController;", "noteId", "", "viewModel", "Lcom/example/notepad/viewmodel/NoteViewModel;", "(Landroidx/navigation/NavHostController;Ljava/lang/Integer;Lcom/example/notepad/viewmodel/NoteViewModel;)V", "AudioPlayerCard", "audioUri", "Landroid/net/Uri;", "onDelete", "Lkotlin/Function0;", "CustomBottomAppBar", "onImageClick", "onVoiceClick", "DisplayImage", "selectedImageUri", "onClick", "ImageSelectionBottomSheet", "imageResources", "", "onImageSelected", "Lkotlin/Function1;", "onDismissRequest", "PreviewAddNoteScreen", "TextDescription", "description", "", "onDescriptionChange", "TextTitle", "title", "onTitleChange", "TopBar", "onThemeClick", "app_debug"})
public final class AddNoteKt {
    
    @androidx.compose.runtime.Composable
    public static final void AddNoteScreen(@org.jetbrains.annotations.NotNull
    androidx.navigation.NavHostController navHostController, @org.jetbrains.annotations.Nullable
    java.lang.Integer noteId, @org.jetbrains.annotations.NotNull
    com.example.notepad.viewmodel.NoteViewModel viewModel) {
    }
    
    @androidx.compose.runtime.Composable
    public static final void TextTitle(@org.jetbrains.annotations.NotNull
    java.lang.String title, @org.jetbrains.annotations.NotNull
    kotlin.jvm.functions.Function1<? super java.lang.String, kotlin.Unit> onTitleChange) {
    }
    
    @androidx.compose.runtime.Composable
    public static final void TextDescription(@org.jetbrains.annotations.NotNull
    java.lang.String description, @org.jetbrains.annotations.NotNull
    kotlin.jvm.functions.Function1<? super java.lang.String, kotlin.Unit> onDescriptionChange) {
    }
    
    @androidx.compose.runtime.Composable
    public static final void TopBar(@org.jetbrains.annotations.NotNull
    kotlin.jvm.functions.Function0<kotlin.Unit> onThemeClick, @org.jetbrains.annotations.NotNull
    androidx.navigation.NavHostController navHostController) {
    }
    
    @kotlin.OptIn(markerClass = {androidx.compose.material3.ExperimentalMaterial3Api.class})
    @androidx.compose.runtime.Composable
    public static final void ImageSelectionBottomSheet(@org.jetbrains.annotations.NotNull
    java.util.List<java.lang.Integer> imageResources, @org.jetbrains.annotations.NotNull
    kotlin.jvm.functions.Function1<? super java.lang.Integer, kotlin.Unit> onImageSelected, @org.jetbrains.annotations.NotNull
    kotlin.jvm.functions.Function0<kotlin.Unit> onDismissRequest) {
    }
    
    @kotlin.OptIn(markerClass = {androidx.compose.material3.ExperimentalMaterial3Api.class})
    @androidx.compose.runtime.Composable
    public static final void CustomBottomAppBar(@org.jetbrains.annotations.NotNull
    kotlin.jvm.functions.Function0<kotlin.Unit> onImageClick, @org.jetbrains.annotations.NotNull
    kotlin.jvm.functions.Function0<kotlin.Unit> onVoiceClick) {
    }
    
    @androidx.compose.runtime.Composable
    public static final void DisplayImage(@org.jetbrains.annotations.Nullable
    android.net.Uri selectedImageUri, @org.jetbrains.annotations.NotNull
    kotlin.jvm.functions.Function0<kotlin.Unit> onClick) {
    }
    
    @androidx.compose.runtime.Composable
    public static final void AudioPlayerCard(@org.jetbrains.annotations.NotNull
    android.net.Uri audioUri, @org.jetbrains.annotations.NotNull
    kotlin.jvm.functions.Function0<kotlin.Unit> onDelete) {
    }
    
    @androidx.compose.ui.tooling.preview.Preview(showSystemUi = true, showBackground = true)
    @androidx.compose.runtime.Composable
    public static final void PreviewAddNoteScreen() {
    }
}