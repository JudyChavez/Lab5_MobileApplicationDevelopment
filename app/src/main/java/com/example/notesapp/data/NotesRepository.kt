package com.example.notesapp.data

import kotlinx.coroutines.flow.Flow

/**
 * Repository that provides insert, update, delete, and retrieve of [Note] from a given data source.
 */
interface NotesRepository {
    //functions in the interface, which map to the DAO implementation.

    /**
     * Retrieve all the notes from the the given data source.
     */
    fun getAllNotesStream(): Flow<List<Note>>

    /**
     * Retrieve a note from the given data source that matches with the [id].
     */
    fun getNoteStream(id: Int): Flow<Note?>

    /**
     * Insert note in the data source
     */
    suspend fun insertNote(note: Note)

    /**
     * Delete note from the data source
     */
    suspend fun deleteNote(note: Note)

    /**
     * Update note in the data source
     */
    suspend fun updateNote(note: Note)
}