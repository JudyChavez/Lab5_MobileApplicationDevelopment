package com.example.notesapp.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "notes")
data class Note (
    @PrimaryKey(autoGenerate = true) //true, Room will automatically generate a unique value for the primary key column when a new entity instance is inserted into the database.
    val id: Int = 0, //default value of 0, necessary for the id to auto generate id values.
    val title: String,
    val content: String,
    val timestamp: Int,
)