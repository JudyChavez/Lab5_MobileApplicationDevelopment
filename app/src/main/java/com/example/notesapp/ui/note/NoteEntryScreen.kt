//package com.example.notesapp.ui.note
//
//import androidx.compose.foundation.layout.fillMaxWidth
//import androidx.compose.foundation.layout.padding
//import androidx.compose.foundation.text.KeyboardOptions
//import androidx.compose.material3.ExperimentalMaterial3Api
//import androidx.compose.material3.MaterialTheme
//import androidx.compose.material3.OutlinedTextField
//import androidx.compose.material3.OutlinedTextFieldDefaults
//import androidx.compose.material3.Scaffold
//import androidx.compose.material3.Text
//import androidx.compose.runtime.Composable
//import androidx.compose.ui.Modifier
//import androidx.compose.ui.res.dimensionResource
//import androidx.compose.ui.res.stringResource
//import androidx.compose.ui.text.input.KeyboardType
//import androidx.lifecycle.viewmodel.compose.viewModel
//import com.example.notesapp.NotesTopAppBar
//import com.example.notesapp.R
//import com.example.notesapp.ui.AppViewModelProvider
////import com.example.notesapp.ui.navigation.NavigationDestination
//import java.util.Currency
//import java.util.Locale
//
//
//import androidx.compose.foundation.layout.Arrangement
//import androidx.compose.foundation.layout.Column
//import androidx.compose.foundation.layout.calculateEndPadding
//import androidx.compose.foundation.layout.calculateStartPadding
//
//import androidx.compose.foundation.rememberScrollState
//
//import androidx.compose.foundation.verticalScroll
//import androidx.compose.material3.Button
//
//import androidx.compose.ui.platform.LocalLayoutDirection
//
//
//
//
////object NoteEntryDestination : NavigationDestination {
////    override val route = "note_entry"
////    override val titleRes = R.string.item_entry_title
////}
//
//@OptIn(ExperimentalMaterial3Api::class)
//@Composable
//fun NoteEntryScreen(
//    navigateBack: () -> Unit,
//    onNavigateUp: () -> Unit,
//    canNavigateBack: Boolean = true,
//    viewModel: NoteEntryViewModel = viewModel(factory = AppViewModelProvider.Factory)
//) {
//    Scaffold(
//        topBar = {
//            NotesTopAppBar(
//                title = stringResource(R.string.app_name),
//                canNavigateBack = canNavigateBack,
//                navigateUp = onNavigateUp
//            )
//        }
//    ) { innerPadding ->
//        NoteEntryBody(
//            noteUiState = viewModel.noteUiState,
//            onNoteValueChange = viewModel::updateUiState,
//            onSaveClick = { },
//            modifier = Modifier
//                .padding(
//                    start = innerPadding.calculateStartPadding(LocalLayoutDirection.current),
//                    end = innerPadding.calculateEndPadding(LocalLayoutDirection.current),
//                    top = innerPadding.calculateTopPadding()
//                )
//                .verticalScroll(rememberScrollState())
//                .fillMaxWidth()
//        )
//    }
//}
//
//@Composable
//fun NoteEntryBody(
//    noteUiState: NoteUiState,
//    onNoteValueChange: (NoteDetails) -> Unit,
//    onSaveClick: () -> Unit,
//    modifier: Modifier = Modifier
//) {
//    Column(
//        verticalArrangement = Arrangement.spacedBy(dimensionResource(id = R.dimen.padding_large)),
//        modifier = modifier.padding(dimensionResource(id = R.dimen.padding_medium))
//    ) {
//        NoteInputForm(
//            noteDetails = noteUiState.noteDetails,
//            onValueChange = onNoteValueChange,
//            modifier = Modifier.fillMaxWidth()
//        )
//        Button(
//            onClick = onSaveClick,
//            enabled = noteUiState.isEntryValid,
//            shape = MaterialTheme.shapes.small,
//            modifier = Modifier.fillMaxWidth()
//        ) {
//            Text(text = stringResource(R.string.save_action))
//        }
//    }
//}
//
//@Composable
//fun NoteInputForm(
//    noteDetails: NoteDetails,
//    modifier: Modifier = Modifier,
//    onValueChange: (NoteDetails) -> Unit = {},
//    enabled: Boolean = true
//) {
//    Column(
//        modifier = modifier,
//        verticalArrangement = Arrangement.spacedBy(dimensionResource(id = R.dimen.padding_medium))
//    ) {
//        OutlinedTextField(
//            value = noteDetails.title,
//            onValueChange = { onValueChange(noteDetails.copy(title = it)) },
//            label = { Text(stringResource(R.string.item_name_req)) },
//            colors = OutlinedTextFieldDefaults.colors(
//                focusedContainerColor = MaterialTheme.colorScheme.secondaryContainer,
//                unfocusedContainerColor = MaterialTheme.colorScheme.secondaryContainer,
//                disabledContainerColor = MaterialTheme.colorScheme.secondaryContainer,
//            ),
//            modifier = Modifier.fillMaxWidth(),
//            enabled = enabled,
//            singleLine = true
//        )
//        OutlinedTextField(
//            value = noteDetails.content,
//            onValueChange = { onValueChange(noteDetails.copy(content = it)) },
//            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Decimal),
//            label = { Text(stringResource(R.string.item_price_req)) },
//            colors = OutlinedTextFieldDefaults.colors(
//                focusedContainerColor = MaterialTheme.colorScheme.secondaryContainer,
//                unfocusedContainerColor = MaterialTheme.colorScheme.secondaryContainer,
//                disabledContainerColor = MaterialTheme.colorScheme.secondaryContainer,
//            ),
//            leadingIcon = { Text(Currency.getInstance(Locale.getDefault()).symbol) },
//            modifier = Modifier.fillMaxWidth(),
//            enabled = enabled,
//            singleLine = true
//        )
//        OutlinedTextField(
//            value = noteDetails.timestamp,
//            onValueChange = { onValueChange(noteDetails.copy(timestamp = it)) },
//            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
//            label = { Text(stringResource(R.string.quantity_req)) },
//            colors = OutlinedTextFieldDefaults.colors(
//                focusedContainerColor = MaterialTheme.colorScheme.secondaryContainer,
//                unfocusedContainerColor = MaterialTheme.colorScheme.secondaryContainer,
//                disabledContainerColor = MaterialTheme.colorScheme.secondaryContainer,
//            ),
//            modifier = Modifier.fillMaxWidth(),
//            enabled = enabled,
//            singleLine = true
//        )
//        if (enabled) {
//            Text(
//                text = stringResource(R.string.required_fields),
//                modifier = Modifier.padding(start = dimensionResource(id = R.dimen.padding_medium))
//            )
//        }
//    }
//}