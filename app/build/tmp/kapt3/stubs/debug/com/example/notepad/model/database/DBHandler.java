package com.example.notepad.model.database;

@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000\u0014\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\b\'\u0018\u0000 \u00052\u00020\u0001:\u0001\u0005B\u0005\u00a2\u0006\u0002\u0010\u0002J\b\u0010\u0003\u001a\u00020\u0004H&\u00a8\u0006\u0006"}, d2 = {"Lcom/example/notepad/model/database/DBHandler;", "Landroidx/room/RoomDatabase;", "()V", "dao", "Lcom/example/notepad/model/database/NoteDao;", "Companion", "app_debug"})
@androidx.room.Database(entities = {com.example.notepad.model.database.NoteEntity.class}, version = 2)
public abstract class DBHandler extends androidx.room.RoomDatabase {
    @org.jetbrains.annotations.NotNull
    private static final java.lang.String DB_NAME = "NoteDatabase.db";
    @org.jetbrains.annotations.NotNull
    public static final java.lang.String TABLE_NAME = "NoteTable";
    public static final int DB_VERSION = 2;
    @org.jetbrains.annotations.Nullable
    private static com.example.notepad.model.database.DBHandler INSTANCE;
    @org.jetbrains.annotations.NotNull
    public static final com.example.notepad.model.database.DBHandler.Companion Companion = null;
    
    public DBHandler() {
        super();
    }
    
    @org.jetbrains.annotations.NotNull
    public abstract com.example.notepad.model.database.NoteDao dao();
    
    @kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000&\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0000\n\u0002\u0010\b\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002\u00a2\u0006\u0002\u0010\u0002J\u000e\u0010\n\u001a\u00020\b2\u0006\u0010\u000b\u001a\u00020\fR\u000e\u0010\u0003\u001a\u00020\u0004X\u0082T\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0005\u001a\u00020\u0006X\u0086T\u00a2\u0006\u0002\n\u0000R\u0010\u0010\u0007\u001a\u0004\u0018\u00010\bX\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u000e\u0010\t\u001a\u00020\u0004X\u0086T\u00a2\u0006\u0002\n\u0000\u00a8\u0006\r"}, d2 = {"Lcom/example/notepad/model/database/DBHandler$Companion;", "", "()V", "DB_NAME", "", "DB_VERSION", "", "INSTANCE", "Lcom/example/notepad/model/database/DBHandler;", "TABLE_NAME", "getDatabase", "context", "Landroid/content/Context;", "app_debug"})
    public static final class Companion {
        
        private Companion() {
            super();
        }
        
        @org.jetbrains.annotations.NotNull
        public final com.example.notepad.model.database.DBHandler getDatabase(@org.jetbrains.annotations.NotNull
        android.content.Context context) {
            return null;
        }
    }
}