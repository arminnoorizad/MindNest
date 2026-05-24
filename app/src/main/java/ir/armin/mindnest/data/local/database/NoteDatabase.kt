package ir.armin.mindnest.data.local.database

import androidx.room.Database
import androidx.room.RoomDatabase
import ir.armin.mindnest.data.local.database.daos.NoteDao
import ir.armin.mindnest.data.local.database.entity.NoteEntity

@Database(
    entities = [NoteEntity::class],
    version = NoteDatabase.DATABASE_VERSION,
    exportSchema = true

)
abstract class NoteDatabase : RoomDatabase() {

    companion object {

        const val TABLE_NAME = "note"
        const val DATABASE_NAME = "Mind_db"
        const val DATABASE_VERSION = 1

    }

    abstract fun noteDao(): NoteDao


}