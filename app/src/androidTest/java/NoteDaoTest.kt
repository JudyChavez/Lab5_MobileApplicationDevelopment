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

        //Declare notes in the class NoteDaoTest for the database to use
        private var note1 = Note(1, "Title1", "Content1", 1)
        private var note2 = Note(2, "Title2", "Content2", 2)

        //a function to create the database and annotate it with @Before so that it can run before every test.
        @Before
        fun createDb() {
            val context: Context = ApplicationProvider.getApplicationContext()
            // Using an in-memory database because the information stored here disappears when the
            // process is killed. To do so, you use the inMemoryDatabaseBuilder()
            notesDatabase = Room.inMemoryDatabaseBuilder(context, NotesDatabase::class.java)
                // Allowing main thread queries, just for testing.
                .allowMainThreadQueries() //You are running the DAO queries in the main thread with .allowMainThreadQueries(), just for testing.
                .build()
            noteDao = notesDatabase.noteDao() //initialize noteDao
        }

        //function to close the database. Annotate it with @After to close the database and run after every test.
        @After
        @Throws(IOException::class)
        fun closeDb() {
            notesDatabase.close()
        }

        //utility functions to add one note, and then two notes, to the database.
        // Later, you use these functions in your test.
        // Mark them as suspend so they can run in a coroutine.
        private suspend fun addOneNoteToDb() {
            noteDao.insert(note1)
        }
        private suspend fun addTwoNotesToDb() {
            noteDao.insert(note1)
            noteDao.insert(note2)
        }

        //test for inserting a single note into the database, insert()
        @Test
        @Throws(Exception::class)
        //daoInsert_insertsNoteIntoDB()
        fun testInsertNote() = runBlocking { //You run the test in a new coroutine with runBlocking{}. This setup is the reason you mark the utility functions as suspend.
            addOneNoteToDb() //utility function
            val allNotes = noteDao.getAllNotes().first() //read the first item in the database.
            assertEquals(allNotes[0], note1) //compare the expected value with the actual value.
        }

        //you add two items to the database inside a coroutine. Then you read the two items and compare them with the expected values.
        @Test
        @Throws(Exception::class)
        //daoGetAllNotes_returnsAllNotesFromDB()
        fun testRetrieveNotes() = runBlocking {
            addTwoNotesToDb()
            val allNotes = noteDao.getAllNotes().first()
            assertEquals(allNotes[0], note1)
            assertEquals(allNotes[1], note2)
        }

        //test for the DAO function to update an entity.
        @Test
        @Throws(Exception::class)
        //daoUpdateNotes_updatesNotesInDB()
        fun testUpdateNote() = runBlocking { //Define the function and create a runBlocking block.
            addTwoNotesToDb()

            //Update the two entities with different values, calling noteDao.update.
            noteDao.update(Note(1, "Title Apples", "Content Apples", 10))
            noteDao.update(Note(2, "Title Bananas", "Content Bananas", 20))

            //Retrieve the entities with itemDao.getAllItems().
                // Compare them to the updated entity and assert.
            val allNotes = noteDao.getAllNotes().first()
            assertEquals(allNotes[0], Note(1, "Title Apples", "Content Apples", 10))
            assertEquals(allNotes[1], Note(2, "Title Bananas", "Content Bananas", 20))
        }

        @Test
        @Throws(Exception::class)
        //daoDeleteNotes_deletesAllNotesFromDB()
        fun testDeleteNote() = runBlocking {
            //Add two items to the database and call itemDao.delete() on those two items to delete them from the database.
            addTwoNotesToDb()
            noteDao.delete(note1)
            noteDao.delete(note2)
            //Retrieve the entities from the database and check that the list is empty.
            val allNotes = noteDao.getAllNotes().first()
            assertTrue(allNotes.isEmpty())
        }

//Ignore for this lab: not needed for this lab, leaving for reference.
//        @Test
//        @Throws(Exception::class)
//        //testRetrieveNote()
//        fun daoGetNote_returnsNoteFromDB() = runBlocking {
//            addOneNoteToDb()
//            //Retrieve the entity from the database using the noteDao.getNote() function and set it to a val named note.
//            val note = noteDao.getNote(1)
//            assertEquals(note.first(), note1)
//        }
    }