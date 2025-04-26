package com.example.notesapp.data

import android.content.Context

/**
 * App container for Dependency injection.
 */
//instantiate the database and pass in the DAO instance to the OfflineNotesRepository class.
interface AppContainer {
    val notesRepository: NotesRepository
}

/**
 * [AppContainer] implementation that provides instance of [OfflineNotesRepository]
 */
class AppDataContainer(private val context: Context) : AppContainer {
    /**
     * Implementation for [NotesRepository]
     */
    //Pass in the NoteDao() instance to the OfflineNotesRepository constructor.
    //Instantiate the database instance
        // by calling getDatabase() on the NotesDatabase class
        // passing in the context and call .noteDao() to create the instance of Dao.
    override val notesRepository: NotesRepository by lazy {
        OfflineNotesRepository(NotesDatabase.getDatabase(context).noteDao())
    }
}