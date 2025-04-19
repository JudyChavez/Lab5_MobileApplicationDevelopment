package com.example.notesapp.ui.note

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.example.notesapp.data.Note
import com.example.notesapp.data.NotesRepository

/**
 * ViewModel to validate and insert items in the Room database.
 */
class NoteEntryViewModel (
    private val notesRepository: NotesRepository
) : ViewModel() {

    /**
     * Holds current item ui state
     */
    var noteUiState by mutableStateOf(NoteUiState())
        private set

    /**
     * Updates the [noteUiState] with the value provided in the argument. This method also triggers
     * a validation for input values.
     */
    fun updateUiState(noteDetails: NoteDetails) {
        noteUiState =
            NoteUiState(noteDetails = noteDetails, isEntryValid = validateInput(noteDetails))
    }

    private fun validateInput(uiState: NoteDetails = noteUiState.noteDetails): Boolean {
        return with(uiState) {
            title.isNotBlank() && content.isNotBlank() && timestamp.isNotBlank()// != 0
        }
    }

    suspend fun saveNote() {
        if (validateInput()) {
            notesRepository.insertNote(noteUiState.noteDetails.toNote())
        }
    }
}

/**
 * Represents Ui State for an Item.
 */
data class NoteUiState(
    val noteDetails: NoteDetails = NoteDetails(),
    val isEntryValid: Boolean = false
)

data class NoteDetails(
    val id: Int = 0,
    val title: String = "",
    val content: String = "",
    val timestamp: String = "",//val timestamp: Int = (System.currentTimeMillis() / 1000).toInt(),
)

/**
 * Extension function to convert [ItemDetails] to [Item]. If the value of [ItemDetails.price] is
 * not a valid [Double], then the price will be set to 0.0. Similarly if the value of
 * [ItemDetails.quantity] is not a valid [Int], then the quantity will be set to 0
 */
fun NoteDetails.toNote(): Note = Note(
    id = id,
    title = title,
    content = content,
    timestamp = timestamp.toIntOrNull() ?: 0
)



/**
 * Extension function to convert [Note] to [NoteUiState]
 */
fun Note.toNoteUiState(isEntryValid: Boolean = false): NoteUiState = NoteUiState(
    noteDetails = this.toNoteDetails(),
    isEntryValid = isEntryValid
)

/**
 * Extension function to convert [Item] to [ItemDetails]
 */
fun Note.toNoteDetails(): NoteDetails = NoteDetails(
    id = id,
    title = title,
    content = content,
    timestamp = timestamp.toString()
)