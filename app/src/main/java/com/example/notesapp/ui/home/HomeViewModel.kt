package com.example.notesapp.ui.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.notesapp.data.Note
import com.example.notesapp.data.NotesRepository
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

/**
 * ViewModel to retrieve all items in the Room database.
 */
class HomeViewModel(
    private val notesRepository: NotesRepository
) : ViewModel() {
    val homeUiState: StateFlow<HomeUiState> =
        notesRepository.getAllNotesStream().map { HomeUiState(it) }
            //Use the stateIn operator to convert the Flow into a StateFlow.
            // The StateFlow is the observable API for UI state, which enables the UI to update itself.
            .stateIn(
                scope = viewModelScope,
                started = SharingStarted.WhileSubscribed(TIMEOUT_MILLIS),
                initialValue = HomeUiState()
            )

    fun updateNote(note: Note) {
        viewModelScope.launch {
            notesRepository.updateNote(note)
        }
    }

    fun insertNote(note: Note) {
        viewModelScope.launch {
            notesRepository.insertNote(note)
        }
    }

    fun deleteNote(note: Note) {
        viewModelScope.launch {
            notesRepository.deleteNote(note)
        }
    }

    companion object {
        private const val TIMEOUT_MILLIS = 5_000L
    }
}

/**
 * Ui State for HomeScreen
 */
//a list of items as a constructor parameter
data class HomeUiState(val noteList: List<Note> = listOf())

