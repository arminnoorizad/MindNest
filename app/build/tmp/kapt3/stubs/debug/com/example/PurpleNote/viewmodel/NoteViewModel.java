package com.example.PurpleNote.viewmodel;

@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000`\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0010\u000b\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010 \n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0007\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0010\b\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0000\n\u0002\u0018\u0002\n\u0002\b\b\b\u0017\u0018\u00002\u00020\u0001B\u000f\b\u0007\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\u0002\u0010\u0004J\u000e\u0010\u001d\u001a\u00020\u001e2\u0006\u0010\u0015\u001a\u00020\u000bJ\u000e\u0010\u001f\u001a\u00020\u001e2\u0006\u0010 \u001a\u00020!J\u000e\u0010\"\u001a\u00020\u001e2\u0006\u0010#\u001a\u00020$JK\u0010%\u001a\u00020&2\u0006\u0010\'\u001a\u00020$2\u0006\u0010(\u001a\u00020$2\u0006\u0010)\u001a\u00020$2\b\u0010*\u001a\u0004\u0018\u00010$2\b\u0010+\u001a\u0004\u0018\u00010$2\u0006\u0010,\u001a\u00020$2\n\b\u0002\u0010 \u001a\u0004\u0018\u00010!\u00a2\u0006\u0002\u0010-R\u001d\u0010\u0005\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\b0\u00070\u0006X\u0082\u0004\u00f8\u0001\u0000\u00a2\u0006\u0002\n\u0000R\u0016\u0010\t\u001a\n\u0012\u0006\u0012\u0004\u0018\u00010\u000b0\nX\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u001a\u0010\f\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u000b0\r0\u0006X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u0014\u0010\u000e\u001a\b\u0012\u0004\u0012\u00020\b0\u0006X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u001d\u0010\u000f\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u000b0\r0\u0010\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0011\u0010\u0012R \u0010\u0013\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\b0\u00070\u0010\u00f8\u0001\u0000\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0014\u0010\u0012R\u0019\u0010\u0015\u001a\n\u0012\u0006\u0012\u0004\u0018\u00010\u000b0\u00168F\u00a2\u0006\u0006\u001a\u0004\b\u0017\u0010\u0018R\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u001d\u0010\u0019\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u000b0\r0\u00108F\u00a2\u0006\u0006\u001a\u0004\b\u001a\u0010\u0012R\u0017\u0010\u001b\u001a\b\u0012\u0004\u0012\u00020\b0\u00108F\u00a2\u0006\u0006\u001a\u0004\b\u001c\u0010\u0012\u0082\u0002\u0004\n\u0002\b\u0019\u00a8\u0006."}, d2 = {"Lcom/example/PurpleNote/viewmodel/NoteViewModel;", "Landroidx/lifecycle/ViewModel;", "repository", "Lcom/example/PurpleNote/model/repository/NoteRepository;", "(Lcom/example/PurpleNote/model/repository/NoteRepository;)V", "_deleteStatus", "Landroidx/lifecycle/MutableLiveData;", "Lkotlin/Result;", "", "_note", "Landroidx/compose/runtime/MutableState;", "Lcom/example/PurpleNote/model/database/NoteEntity;", "_searchResults", "", "_upsertStatus", "allNotes", "Landroidx/lifecycle/LiveData;", "getAllNotes", "()Landroidx/lifecycle/LiveData;", "deleteStatus", "getDeleteStatus", "note", "Landroidx/compose/runtime/State;", "getNote", "()Landroidx/compose/runtime/State;", "searchResults", "getSearchResults", "upsertStatus", "getUpsertStatus", "deleteNote", "", "getNoteById", "noteId", "", "searchNotes", "searchQuery", "", "upsertNote", "Lkotlinx/coroutines/Job;", "title", "description", "date", "image", "voiceUri", "category", "(Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/Integer;)Lkotlinx/coroutines/Job;", "app_debug"})
@dagger.hilt.android.lifecycle.HiltViewModel
public class NoteViewModel extends androidx.lifecycle.ViewModel {
    @org.jetbrains.annotations.NotNull
    private final com.example.PurpleNote.model.repository.NoteRepository repository = null;
    @org.jetbrains.annotations.NotNull
    private final androidx.lifecycle.LiveData<java.util.List<com.example.PurpleNote.model.database.NoteEntity>> allNotes = null;
    @org.jetbrains.annotations.NotNull
    private final androidx.lifecycle.MutableLiveData<java.lang.Boolean> _upsertStatus = null;
    @org.jetbrains.annotations.NotNull
    private final androidx.lifecycle.MutableLiveData<java.util.List<com.example.PurpleNote.model.database.NoteEntity>> _searchResults = null;
    @org.jetbrains.annotations.NotNull
    private final androidx.lifecycle.MutableLiveData<kotlin.Result<java.lang.Boolean>> _deleteStatus = null;
    @org.jetbrains.annotations.NotNull
    private final androidx.lifecycle.LiveData<kotlin.Result<java.lang.Boolean>> deleteStatus = null;
    @org.jetbrains.annotations.NotNull
    private final androidx.compose.runtime.MutableState<com.example.PurpleNote.model.database.NoteEntity> _note = null;
    
    @javax.inject.Inject
    public NoteViewModel(@org.jetbrains.annotations.NotNull
    com.example.PurpleNote.model.repository.NoteRepository repository) {
        super();
    }
    
    @org.jetbrains.annotations.NotNull
    public final androidx.lifecycle.LiveData<java.util.List<com.example.PurpleNote.model.database.NoteEntity>> getAllNotes() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull
    public final androidx.lifecycle.LiveData<java.lang.Boolean> getUpsertStatus() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull
    public final androidx.lifecycle.LiveData<java.util.List<com.example.PurpleNote.model.database.NoteEntity>> getSearchResults() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull
    public final androidx.lifecycle.LiveData<kotlin.Result<java.lang.Boolean>> getDeleteStatus() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull
    public final androidx.compose.runtime.State<com.example.PurpleNote.model.database.NoteEntity> getNote() {
        return null;
    }
    
    public final void getNoteById(int noteId) {
    }
    
    @org.jetbrains.annotations.NotNull
    public final kotlinx.coroutines.Job upsertNote(@org.jetbrains.annotations.NotNull
    java.lang.String title, @org.jetbrains.annotations.NotNull
    java.lang.String description, @org.jetbrains.annotations.NotNull
    java.lang.String date, @org.jetbrains.annotations.Nullable
    java.lang.String image, @org.jetbrains.annotations.Nullable
    java.lang.String voiceUri, @org.jetbrains.annotations.NotNull
    java.lang.String category, @org.jetbrains.annotations.Nullable
    java.lang.Integer noteId) {
        return null;
    }
    
    public final void searchNotes(@org.jetbrains.annotations.NotNull
    java.lang.String searchQuery) {
    }
    
    public final void deleteNote(@org.jetbrains.annotations.NotNull
    com.example.PurpleNote.model.database.NoteEntity note) {
    }
}