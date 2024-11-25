package com.example.mindNest.model.database;

@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u00004\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0010 \n\u0002\b\u0002\n\u0002\u0010\b\n\u0002\b\u0003\n\u0002\u0010\u000e\n\u0002\b\u0003\bg\u0018\u00002\u00020\u0001J\u0019\u0010\u0002\u001a\u00020\u00032\u0006\u0010\u0004\u001a\u00020\u0005H\u00a7@\u00f8\u0001\u0000\u00a2\u0006\u0002\u0010\u0006J\u0014\u0010\u0007\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00050\t0\bH\'J\u001b\u0010\n\u001a\u0004\u0018\u00010\u00052\u0006\u0010\u000b\u001a\u00020\fH\u00a7@\u00f8\u0001\u0000\u00a2\u0006\u0002\u0010\rJ\u001f\u0010\u000e\u001a\b\u0012\u0004\u0012\u00020\u00050\t2\u0006\u0010\u000f\u001a\u00020\u0010H\u00a7@\u00f8\u0001\u0000\u00a2\u0006\u0002\u0010\u0011J\u0019\u0010\u0012\u001a\u00020\u00032\u0006\u0010\u0004\u001a\u00020\u0005H\u00a7@\u00f8\u0001\u0000\u00a2\u0006\u0002\u0010\u0006\u0082\u0002\u0004\n\u0002\b\u0019\u00a8\u0006\u0013"}, d2 = {"Lcom/example/mindNest/model/database/NoteDao;", "", "deleteNote", "", "noteEntity", "Lcom/example/mindNest/model/database/NoteEntity;", "(Lcom/example/mindNest/model/database/NoteEntity;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "getAllNotes", "Lkotlinx/coroutines/flow/Flow;", "", "getNoteById", "id", "", "(ILkotlin/coroutines/Continuation;)Ljava/lang/Object;", "searchNotes", "searchQuery", "", "(Ljava/lang/String;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "upsertNote", "app_debug"})
@androidx.room.Dao
public abstract interface NoteDao {
    
    @androidx.room.Upsert
    @org.jetbrains.annotations.Nullable
    public abstract java.lang.Object upsertNote(@org.jetbrains.annotations.NotNull
    com.example.mindNest.model.database.NoteEntity noteEntity, @org.jetbrains.annotations.NotNull
    kotlin.coroutines.Continuation<? super kotlin.Unit> $completion);
    
    @androidx.room.Delete
    @org.jetbrains.annotations.Nullable
    public abstract java.lang.Object deleteNote(@org.jetbrains.annotations.NotNull
    com.example.mindNest.model.database.NoteEntity noteEntity, @org.jetbrains.annotations.NotNull
    kotlin.coroutines.Continuation<? super kotlin.Unit> $completion);
    
    @androidx.room.Query(value = "SELECT * FROM NoteTable ORDER BY date DESC")
    @org.jetbrains.annotations.NotNull
    public abstract kotlinx.coroutines.flow.Flow<java.util.List<com.example.mindNest.model.database.NoteEntity>> getAllNotes();
    
    @androidx.room.Query(value = "SELECT * FROM NoteTable WHERE title LIKE :searchQuery OR description LIKE :searchQuery ")
    @org.jetbrains.annotations.Nullable
    public abstract java.lang.Object searchNotes(@org.jetbrains.annotations.NotNull
    java.lang.String searchQuery, @org.jetbrains.annotations.NotNull
    kotlin.coroutines.Continuation<? super java.util.List<com.example.mindNest.model.database.NoteEntity>> $completion);
    
    @androidx.room.Query(value = "SELECT * FROM NoteTable WHERE id = :id")
    @org.jetbrains.annotations.Nullable
    public abstract java.lang.Object getNoteById(int id, @org.jetbrains.annotations.NotNull
    kotlin.coroutines.Continuation<? super com.example.mindNest.model.database.NoteEntity> $completion);
}