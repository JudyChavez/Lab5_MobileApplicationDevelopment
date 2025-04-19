package com.example.notesapp.data

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow

@Dao
interface NoteDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE) //onConflict tells the Room what to do in case of a conflict. The OnConflictStrategy.IGNORE strategy ignores a new item.
    suspend fun insert(note: Note)

    @Update
    suspend fun update(note: Note)

    @Delete
    suspend fun delete(note: Note)

    //retrieve a particular note from the note table based on the given id.
    @Query("SELECT * FROM notes WHERE id = :id")
    //Note: Flow in Room database can keep the data up-to-date by emitting a notification
    // whenever the data in the database changes. This allows you to observe the data and update your UI accordingly.
    fun getNote(id: Int): Flow<Note> //With Flow as the return type, you receive notification whenever the data in the database changes.

    @Query("SELECT * FROM notes ORDER BY title ASC")
    fun getAllNotes(): Flow<List<Note>>
}