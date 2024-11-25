package com.example.mindNest.model.repository;

@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000<\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0010 \n\u0002\b\u0002\n\u0002\u0010\b\n\u0002\b\u0003\n\u0002\u0010\u000e\n\u0002\b\u0003\u0018\u00002\u00020\u0001B\u000f\b\u0007\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\u0002\u0010\u0004J\u0019\u0010\u0005\u001a\u00020\u00062\u0006\u0010\u0007\u001a\u00020\bH\u0086@\u00f8\u0001\u0000\u00a2\u0006\u0002\u0010\tJ\u0012\u0010\n\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\b0\f0\u000bJ\u001b\u0010\r\u001a\u0004\u0018\u00010\b2\u0006\u0010\u000e\u001a\u00020\u000fH\u0086@\u00f8\u0001\u0000\u00a2\u0006\u0002\u0010\u0010J\u001f\u0010\u0011\u001a\b\u0012\u0004\u0012\u00020\b0\f2\u0006\u0010\u0012\u001a\u00020\u0013H\u0086@\u00f8\u0001\u0000\u00a2\u0006\u0002\u0010\u0014J\u0019\u0010\u0015\u001a\u00020\u00062\u0006\u0010\u0007\u001a\u00020\bH\u0086@\u00f8\u0001\u0000\u00a2\u0006\u0002\u0010\tR\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004\u00a2\u0006\u0002\n\u0000\u0082\u0002\u0004\n\u0002\b\u0019\u00a8\u0006\u0016"}, d2 = {"Lcom/example/mindNest/model/repository/NoteRepository;", "", "noteDao", "Lcom/example/mindNest/model/database/NoteDao;", "(Lcom/example/mindNest/model/database/NoteDao;)V", "deleteNote", "", "note", "Lcom/example/mindNest/model/database/NoteEntity;", "(Lcom/example/mindNest/model/database/NoteEntity;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "getAllNote", "Lkotlinx/coroutines/flow/Flow;", "", "getNoteById", "id", "", "(ILkotlin/coroutines/Continuation;)Ljava/lang/Object;", "searchByTitle", "searchQuery", "", "(Ljava/lang/String;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "upsertNote", "app_debug"})
public final class NoteRepository {
    @org.jetbrains.annotations.NotNull
    private final com.example.mindNest.model.database.NoteDao noteDao = null;
    
    @javax.inject.Inject
    public NoteRepository(@org.jetbrains.annotations.NotNull
    com.example.mindNest.model.database.NoteDao noteDao) {
        super();
    }
    
    @org.jetbrains.annotations.Nullable
    public final java.lang.Object upsertNote(@org.jetbrains.annotations.NotNull
    com.example.mindNest.model.database.NoteEntity note, @org.jetbrains.annotations.NotNull
    kotlin.coroutines.Continuation<? super kotlin.Unit> $completion) {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable
    public final java.lang.Object deleteNote(@org.jetbrains.annotations.NotNull
    com.example.mindNest.model.database.NoteEntity note, @org.jetbrains.annotations.NotNull
    kotlin.coroutines.Continuation<? super kotlin.Unit> $completion) {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull
    public final kotlinx.coroutines.flow.Flow<java.util.List<com.example.mindNest.model.database.NoteEntity>> getAllNote() {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable
    public final java.lang.Object searchByTitle(@org.jetbrains.annotations.NotNull
    java.lang.String searchQuery, @org.jetbrains.annotations.NotNull
    kotlin.coroutines.Continuation<? super java.util.List<com.example.mindNest.model.database.NoteEntity>> $completion) {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable
    public final java.lang.Object getNoteById(int id, @org.jetbrains.annotations.NotNull
    kotlin.coroutines.Continuation<? super com.example.mindNest.model.database.NoteEntity> $completion) {
        return null;
    }
}