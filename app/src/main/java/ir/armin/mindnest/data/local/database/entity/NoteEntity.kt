package ir.armin.mindnest.data.local.database.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import ir.armin.mindnest.data.local.database.NoteDatabase


@Entity(tableName = NoteDatabase.TABLE_NAME)
data class NoteEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0 ,
    val title: String,
    val content: String,
    val imagePath: String? = null,
    val voicePath: String? = null,
    val backgroundImageResId: Int? = null,
)
