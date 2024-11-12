package com.example.PurpleNote.view.ui;

@kotlin.Metadata(mv = {1, 9, 0}, k = 2, xi = 48, d1 = {"\u00008\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0000\u001a\u0010\u0010\u0000\u001a\u00020\u00012\u0006\u0010\u0002\u001a\u00020\u0003H\u0007\u001a$\u0010\u0004\u001a\u00020\u00012\u0006\u0010\u0005\u001a\u00020\u00062\u0012\u0010\u0007\u001a\u000e\u0012\u0004\u0012\u00020\u0006\u0012\u0004\u0012\u00020\u00010\bH\u0007\u001a\u001a\u0010\t\u001a\u00020\u00012\u0006\u0010\n\u001a\u00020\u000b2\b\b\u0002\u0010\f\u001a\u00020\rH\u0007\u001a2\u0010\u000e\u001a\u00020\u00012\u0006\u0010\u0002\u001a\u00020\u00032\f\u0010\u000f\u001a\b\u0012\u0004\u0012\u00020\u00010\u00102\u0012\u0010\u0011\u001a\u000e\u0012\u0004\u0012\u00020\u0012\u0012\u0004\u0012\u00020\u00010\bH\u0007\u00a8\u0006\u0013"}, d2 = {"NoteItem", "", "note", "Lcom/example/PurpleNote/model/database/NoteEntity;", "SearchTopBar", "searchQuery", "", "onSearchQueryChange", "Lkotlin/Function1;", "ShowNoteScreen", "navHostController", "Landroidx/navigation/NavHostController;", "viewModel", "Lcom/example/PurpleNote/viewmodel/NoteViewModel;", "SwipeToDismissNote", "onDeleteClick", "Lkotlin/Function0;", "onNoteClick", "", "app_debug"})
public final class ShowNoteScreenKt {
    
    @android.annotation.SuppressLint(value = {"UnusedMaterial3ScaffoldPaddingParameter"})
    @androidx.compose.runtime.Composable
    public static final void ShowNoteScreen(@org.jetbrains.annotations.NotNull
    androidx.navigation.NavHostController navHostController, @org.jetbrains.annotations.NotNull
    com.example.PurpleNote.viewmodel.NoteViewModel viewModel) {
    }
    
    @android.annotation.SuppressLint(value = {"UseOfNonLambdaOffsetOverload"})
    @androidx.compose.runtime.Composable
    public static final void SwipeToDismissNote(@org.jetbrains.annotations.NotNull
    com.example.PurpleNote.model.database.NoteEntity note, @org.jetbrains.annotations.NotNull
    kotlin.jvm.functions.Function0<kotlin.Unit> onDeleteClick, @org.jetbrains.annotations.NotNull
    kotlin.jvm.functions.Function1<? super java.lang.Integer, kotlin.Unit> onNoteClick) {
    }
    
    @androidx.compose.runtime.Composable
    public static final void SearchTopBar(@org.jetbrains.annotations.NotNull
    java.lang.String searchQuery, @org.jetbrains.annotations.NotNull
    kotlin.jvm.functions.Function1<? super java.lang.String, kotlin.Unit> onSearchQueryChange) {
    }
    
    @androidx.compose.runtime.Composable
    public static final void NoteItem(@org.jetbrains.annotations.NotNull
    com.example.PurpleNote.model.database.NoteEntity note) {
    }
}