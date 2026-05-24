package ir.armin.mindnest

import android.content.Context
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.espresso.matcher.ViewMatchers.assertThat
import androidx.test.ext.junit.runners.AndroidJUnit4
import ir.armin.mindnest.data.local.database.NoteDatabase

import ir.armin.mindnest.data.local.database.daos.NoteDao
import ir.armin.mindnest.data.local.database.entity.NoteEntity
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import org.hamcrest.CoreMatchers.`is`
import org.hamcrest.CoreMatchers.nullValue
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith





@RunWith(AndroidJUnit4::class)
class NoteDatabaseTest {

    private lateinit var db: NoteDatabase
    private lateinit var noteDao: NoteDao

    @Before
    fun createDb() {
        val context = ApplicationProvider.getApplicationContext<Context>()
        db = Room.inMemoryDatabaseBuilder(context, NoteDatabase::class.java)
            .allowMainThreadQueries()
            .build()
        noteDao = db.noteDao()
    }

    @After
    fun closeDb() {
        db.close()
    }

    @Test
    fun writeNoteAndReadInList() = runBlocking {
        val note = NoteEntity(id = 1, title = "test", content = "content")

        noteDao.upsertNote(note)

        val allNotes = noteDao.getAllNotes().first()
        assert(allNotes.contains(note))
        assert(allNotes[0].title == "test")
    }

    @Test
    fun deleteNoteById_removesNoteFromDatabase() = runBlocking {
        val note = NoteEntity(id = 20, title = "delete", content = "content")
        noteDao.upsertNote(note)

        val deletedRows = noteDao.deleteNoteById(20)

        val loadedNote = noteDao.getNoteById(20)

        assertThat(deletedRows, `is`(1))
        assertThat(loadedNote, `is`(nullValue()))
    }

    @Test
    fun getNoteById_returnsNullForNonExistentId() = runBlocking {
        val note = noteDao.getNoteById(999)
        assertThat(note, `is`(nullValue()))
    }

}
