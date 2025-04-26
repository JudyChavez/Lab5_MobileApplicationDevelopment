package com.example.notesapp.ui.home

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier

import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.notesapp.NotesTopAppBar
import com.example.notesapp.R
import com.example.notesapp.data.Note
import com.example.notesapp.ui.AppViewModelProvider
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.TextButton
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.platform.testTag



/**
 * Entry route for Home screen
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    modifier: Modifier = Modifier,
    viewModel: HomeViewModel = viewModel(factory = AppViewModelProvider.Factory),
    onToggleTheme: () -> Unit,
    isDarkMode: Boolean
) {
    //collect the UI state from the HomeViewModel.
    // You use collectAsState(), which collects values from this StateFlow and represents its latest value via State.
    val homeUiState by viewModel.homeUiState.collectAsState()


    // Edit Dialog state
    var showEditDialog by remember { mutableStateOf(false) }
    var selectedNote by remember { mutableStateOf<Note?>(null) }

    // Add Dialog state
    var showAddDialog by remember { mutableStateOf(false) }

    // Theme state
    var isDarkMode by remember { mutableStateOf(false) }



    Scaffold(
        modifier = modifier,//.nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = {
            NotesTopAppBar(
                title = stringResource(R.string.app_name),//"NotesTopAppBar",//stringResource(HomeDestination.titleRes),
                canNavigateBack = false,
                //scrollBehavior = scrollBehavior,
                onThemeToggle = onToggleTheme,//{ isDarkMode = !isDarkMode } // Toggle theme state
                isDarkMode = isDarkMode
            )
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = { showAddDialog = true },
                shape = MaterialTheme.shapes.medium,
                modifier = Modifier.padding(dimensionResource(id = R.dimen.padding_large))
            ) {
                Icon(
                    imageVector = Icons.Default.Add,
                    contentDescription = stringResource(R.string.note_entry_title)
                )
            }
        },
    ) { innerPadding ->
        // Display List header and List of Items
        HomeBody(
            //To pass the inventory list to this composable,
            // you must retrieve the inventory data from the repository and pass it into the HomeViewModel.
            noteList = homeUiState.noteList,//listOf(), // Empty list is being passed in for itemList
            onNoteClick = {
                selectedNote = it
                showEditDialog = true
            },
            modifier = modifier.fillMaxSize(),
            contentPadding = innerPadding,
        )
        // Show dialog if note is selected
        if (showEditDialog && selectedNote != null) {
            EditNoteDialog(
                note = selectedNote!!,
                onConfirm = { updatedNote ->
                    viewModel.updateNote(updatedNote)
                    showEditDialog = false
                },
                onCancel = { showEditDialog = false },
                onDelete = {
                    viewModel.deleteNote(selectedNote!!)
                    showEditDialog = false
                }
            )
        }
        //Show dialog if floatingActionButton is selected.
        if (showAddDialog) {
            AddNoteDialog(
                onConfirm = { newNote ->
                    viewModel.insertNote(newNote)
                    showAddDialog = false
                },
                onCancel = { showAddDialog = false }
            )
        }
    }
}

@Composable
private fun HomeBody(
    noteList: List<Note>,
    onNoteClick: (Note) -> Unit,
    modifier: Modifier = Modifier,
    contentPadding: PaddingValues = PaddingValues(0.dp),
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier,
    ) {
        if (noteList.isEmpty()) {
            Text(
                text = stringResource(R.string.no_note_description),
                textAlign = TextAlign.Center,
                style = MaterialTheme.typography.titleLarge,
                modifier = Modifier.padding(contentPadding),
            )
        } else {
            NoteList(
                noteList = noteList,
                onNoteClick = onNoteClick, //{},//{ onNoteClick(it.id) },
                contentPadding = contentPadding,
                modifier = Modifier.padding(horizontal = dimensionResource(id = R.dimen.padding_small))
            )
        }
    }
}

@Composable
private fun NoteList(
    noteList: List<Note>,
    onNoteClick: (Note) -> Unit,
    contentPadding: PaddingValues,
    modifier: Modifier = Modifier
) {
    LazyColumn(
        modifier = modifier,
        contentPadding = contentPadding
    ) {
        items(items = noteList, key = { it.id }) { note ->
            NoteItem(
                note = note,
                modifier = Modifier
                    .padding(dimensionResource(id = R.dimen.padding_small))
                    .clickable { onNoteClick(note) }
            )
        }
    }
}

@Composable
private fun NoteItem(
    note: Note, modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier,
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Column(
            modifier = Modifier.padding(dimensionResource(id = R.dimen.padding_large)),
            verticalArrangement = Arrangement.spacedBy(dimensionResource(id = R.dimen.padding_small))
        ) {
            Column(
                modifier = Modifier.fillMaxWidth()
            ) {
//                Text(
//                    text = note.id.toString(),
//                    style = MaterialTheme.typography.titleLarge,
//                )
                Text(
                    text = note.title,
                    style = MaterialTheme.typography.titleLarge,
                )
                Spacer(Modifier.weight(1f))
                Text(
                    text = note.content,
                    style = MaterialTheme.typography.titleMedium
                )
//                Spacer(Modifier.weight(1f))
//                Text(
//                    text = note.timestamp.toString(),
//                    style = MaterialTheme.typography.titleMedium
//                )
            }
        }
    }
}

@Composable
fun EditNoteDialog(
    note: Note,
    onConfirm: (Note) -> Unit,
    onCancel: () -> Unit,
    onDelete: () -> Unit,
) {
    var title by remember { mutableStateOf(note.title) }
    var content by remember { mutableStateOf(note.content) }

    androidx.compose.material3.AlertDialog(
        onDismissRequest = onCancel,
        title = { Text("Edit Note") },
        text = {
            Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                androidx.compose.material3.OutlinedTextField(
                    value = title,
                    onValueChange = { title = it },
                    label = { Text("Title") },
                    modifier = Modifier
                        .fillMaxWidth()
                        .testTag("EditTitleField")
                )
                androidx.compose.material3.OutlinedTextField(
                    value = content,
                    onValueChange = { content = it },
                    label = { Text("Content") },
                    modifier = Modifier
                        .fillMaxWidth()
                        .testTag("EditContentField")
                )
            }
        },

        // Combine Cancel and Delete in one dismissButton block
        dismissButton = {
            Row {
                TextButton(
                    onClick = onDelete,
                    modifier = Modifier.testTag("DeleteNoteButton")
                ) {
                    Text("Delete", color = MaterialTheme.colorScheme.error)
                }
                Spacer(modifier = Modifier.padding(start = 8.dp))
                TextButton(onClick = onCancel) {
                    Text("Cancel")
                }
            }
        },
        confirmButton = {
            TextButton(
                onClick = {
                    val updatedNote = note.copy(title = title, content = content)
                    onConfirm(updatedNote)
                },
                modifier = Modifier.testTag("SaveNoteButton")
            ) {
                Text("Save")
            }
        }
    )
}



@Composable
fun AddNoteDialog(
    onConfirm: (Note) -> Unit,
    onCancel: () -> Unit
) {
    var title by remember { mutableStateOf("") }
    var content by remember { mutableStateOf("") }

    androidx.compose.material3.AlertDialog(
        onDismissRequest = onCancel,
        title = { Text("New Note") },
        text = {
            Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                androidx.compose.material3.OutlinedTextField(
                    value = title,
                    onValueChange = { title = it },
                    label = { Text("Title") },
                    modifier = Modifier
                        .fillMaxWidth()
                        .testTag("AddTitleField")
                )
                androidx.compose.material3.OutlinedTextField(
                    value = content,
                    onValueChange = { content = it },
                    label = { Text("Content") },
                    modifier = Modifier
                        .fillMaxWidth()
                        .testTag("AddContentField")
                )
            }
        },
        confirmButton = {
            androidx.compose.material3.TextButton(onClick = {
                val newNote = Note(
                    id = 0, // Will be auto-generated by Room
                    title = title,
                    content = content,
                    timestamp = (System.currentTimeMillis() / 1000).toInt() // Divide by 1000 to convert to seconds
                )
                onConfirm(newNote)
            }) {
                Text("Add")
            }
        },
        dismissButton = {
            androidx.compose.material3.TextButton(onClick = onCancel) {
                Text("Cancel")
            }
        }
    )
}


