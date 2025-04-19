package com.example.notesapp.data

import androidx.room.Database
import androidx.room.RoomDatabase
import android.content.Context
import androidx.room.Room

//annotation requires several arguments so that Room can build the database.
@Database(
    entities = [Note::class],
    version = 1,
    exportSchema = false
)

abstract class NotesDatabase : RoomDatabase() {

    //declare an abstract function that returns the ItemDao so that the database knows about the DAO.
    abstract fun noteDao(): NoteDao

    //define a companion object,
        // which allows access to the methods to create or get the database and uses the class name as the qualifier.
    companion object {
        //The Instance variable keeps a reference to the database, when one has been created.
            // This helps maintain a single instance of the database opened at a given time,
        @Volatile //The value of a volatile variable is never cached, and all reads and writes are to and from the main memory.
        private var Instance: NotesDatabase? = null

        fun getDatabase(context: Context): NotesDatabase {
            //Wrapping the code to get the database inside a synchronized block
                // means that only one thread of execution at a time can enter this block of code,
                    // which makes sure the database only gets initialized once.
            //Pass in this, the companion object.
            return Instance ?: synchronized(this) {
                //use the database builder to get the database.
                Room.databaseBuilder(context, NotesDatabase::class.java, "notes_database")
                    .fallbackToDestructiveMigration()
                    .build()
                    .also { Instance = it }
            }
        }
    }
}