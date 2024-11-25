package com.example.mindNest.view.ui;

@kotlin.Metadata(mv = {1, 9, 0}, k = 2, xi = 48, d1 = {"\u00008\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0010\u000e\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\u001a+\u0010\u0000\u001a\u00020\u00012\u0006\u0010\u0002\u001a\u00020\u00032\n\b\u0002\u0010\u0004\u001a\u0004\u0018\u00010\u00052\b\b\u0002\u0010\u0006\u001a\u00020\u0007H\u0007\u00a2\u0006\u0002\u0010\b\u001a\u001e\u0010\t\u001a\u00020\u00012\u0006\u0010\n\u001a\u00020\u000b2\f\u0010\f\u001a\b\u0012\u0004\u0012\u00020\u00010\rH\u0007\u001a \u0010\u000e\u001a\u00020\u00012\b\u0010\u000f\u001a\u0004\u0018\u00010\u000b2\f\u0010\u0010\u001a\b\u0012\u0004\u0012\u00020\u00010\rH\u0007\u001a\b\u0010\u0011\u001a\u00020\u0001H\u0007\u001a\u0016\u0010\u0012\u001a\u00020\u00132\u0006\u0010\u0014\u001a\u00020\u00152\u0006\u0010\u0016\u001a\u00020\u000b\u00a8\u0006\u0017"}, d2 = {"AddNoteScreen", "", "navHostController", "Landroidx/navigation/NavHostController;", "noteId", "", "viewModel", "Lcom/example/mindNest/viewmodel/NoteViewModel;", "(Landroidx/navigation/NavHostController;Ljava/lang/Integer;Lcom/example/mindNest/viewmodel/NoteViewModel;)V", "AudioPlayerCard", "audioUri", "Landroid/net/Uri;", "onDelete", "Lkotlin/Function0;", "DisplayImage", "selectedImageUri", "onClick", "PreviewAddNoteScreen", "saveImageToInternalStorage", "", "context", "Landroid/content/Context;", "uri", "app_debug"})
public final class AddNoteKt {
    
    @androidx.compose.runtime.Composable
    public static final void AddNoteScreen(@org.jetbrains.annotations.NotNull
    androidx.navigation.NavHostController navHostController, @org.jetbrains.annotations.Nullable
    java.lang.Integer noteId, @org.jetbrains.annotations.NotNull
    com.example.mindNest.viewmodel.NoteViewModel viewModel) {
    }
    
    @androidx.compose.runtime.Composable
    public static final void DisplayImage(@org.jetbrains.annotations.Nullable
    android.net.Uri selectedImageUri, @org.jetbrains.annotations.NotNull
    kotlin.jvm.functions.Function0<kotlin.Unit> onClick) {
    }
    
    @org.jetbrains.annotations.NotNull
    public static final java.lang.String saveImageToInternalStorage(@org.jetbrains.annotations.NotNull
    android.content.Context context, @org.jetbrains.annotations.NotNull
    android.net.Uri uri) {
        return null;
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