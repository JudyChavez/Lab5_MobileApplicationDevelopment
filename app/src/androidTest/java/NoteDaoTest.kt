package com.example.notesapp

import android.content.Context
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import java.io.IOException



import com.example.notesapp.data.NotesDatabase
import com.example.notesapp.data.NoteDao

import kotlin.jvm.Throws



import com.example.notesapp.data.Note


    @RunWith(AndroidJUnit4::class)
    class NoteDaoTest {
        private lateinit var noteDao: NoteDao
        private lateinit var notesDatabase: NotesDatabase

        //Declare items in the class ItemDaoTest for the database to use
        private var note1 = Note(1, "Title1", "Content1", 1)
        private var note2 = Note(2, "Title2", "Content2", 2)

        //a function to create the database and annotate it with @Before so that it can run before every test.
        @Before
        fun createDb() {
            val context: Context = ApplicationProvider.getApplicationContext()
            // Using an in-memory database because the information stored here disappears when the
            // process is killed.
            notesDatabase = Room.inMemoryDatabaseBuilder(context, NotesDatabase::class.java)
                // Allowing main thread queries, just for testing.
                .allowMainThreadQueries()
                .build()
            noteDao = notesDatabase.noteDao() //initialize itemDao
        }

        //function to close the database. Annotate it with @After to close the database and run after every test.
        @After
        @Throws(IOException::class)
        fun closeDb() {
            notesDatabase.close()
        }

        //utility functions to add one item, and then two items, to the database.
        // Later, you use these functions in your test.
        // Mark them as suspend so they can run in a coroutine.
        private suspend fun addOneNoteToDb() {
            noteDao.insert(note1)
        }
        private suspend fun addTwoNotesToDb() {
            noteDao.insert(note1)
            noteDao.insert(note2)
        }

        //test for inserting a single item into the database, insert()
        @Test
        @Throws(Exception::class)
        fun daoInsert_insertsNoteIntoDB() = runBlocking { //You run the test in a new coroutine with runBlocking{}. This setup is the reason you mark the utility functions as suspend.
            addOneNoteToDb() //utility function
            val allNotes = noteDao.getAllNotes().first() //read the first item in the database.
            assertEquals(allNotes[0], note1) //compare the expected value with the actual value.
        }

        //you add two items to the database inside a coroutine. Then you read the two items and compare them with the expected values.
        @Test
        @Throws(Exception::class)
        fun daoGetAllNotes_returnsAllNotesFromDB() = runBlocking {
            addTwoNotesToDb()
            val allNotes = noteDao.getAllNotes().first()
            assertEquals(allNotes[0], note1)
            assertEquals(allNotes[1], note2)
        }

        //test for the DAO function to update an entity.
        @Test
        @Throws(Exception::class)
        fun daoUpdateNotes_updatesNotesInDB() = runBlocking { //Define the function and create a runBlocking block.
            addTwoNotesToDb()

            //Update the two entities with different values, calling itemDao.update.
            noteDao.update(Note(1, "Title Apples", "Content Apples", 25))
            noteDao.update(Note(2, "Title Bananas", "Content Bananas", 50))

            val allNotes = noteDao.getAllNotes().first()
            assertEquals(allNotes[0], Note(1, "Title Apples", "Content Apples", 25))
            assertEquals(allNotes[1], Note(2, "Title Bananas", "Content Bananas", 50))
        }

        @Test
        @Throws(Exception::class)
        fun daoDeleteNotes_deletesAllNotesFromDB() {
            fun daoDeleteNotes_deletesAllNotesFromDB() = runBlocking {
                addTwoNotesToDb()
                noteDao.delete(note1)
                noteDao.delete(note2)
                //Retrieve the entities from the database and check that the list is empty.
                val allNotes = noteDao.getAllNotes().first()
                assertTrue(allNotes.isEmpty())
            }
        }

        @Test
        @Throws(Exception::class)
        fun daoGetNote_returnsNoteFromDB() = runBlocking {
            addOneNoteToDb()
            //Retrieve the entity from the database using the itemDao.getItem() function and set it to a val named item.
            val note = noteDao.getNote(1)
            assertEquals(note.first(), note1)
        }
    }