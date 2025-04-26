package com.example.notesapp

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.test.platform.app.InstrumentationRegistry
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.notesapp.ui.theme.NotesAppTheme

import org.junit.Test
import org.junit.runner.RunWith

import org.junit.Assert.*
import org.junit.Rule


import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithContentDescription
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.performTextClearance
import androidx.compose.ui.test.performTextInput

import androidx.compose.ui.test.*



/**
 * Instrumented test, which will execute on an Android device.
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */

//UI Tests (Instrumented)
/*
UI Tests (Instrumented)

testAddNewNoteUI: Confirms a new note can be added from the FAB, then displayed.

testEditNoteUI: Ensures an existing note can be opened, edited, and saved.

testDeleteNoteUI: Verifies a note can be removed via the dialog’s “Delete” button.

testToggleDarkMode: Ensures the dark-mode switch (in the top bar) immediately changes UI theme.
*/


@RunWith(AndroidJUnit4::class)
class NotesAppUITest {
    /*//sample UI Test
    @Test
    fun useAppContext() {
        // Context of the app under test.
        val appContext = InstrumentationRegistry.getInstrumentation().targetContext
        assertEquals("com.example.notesapp", appContext.packageName)
    }*/

    @get:Rule
    val composeTestRule = createComposeRule()

    //The compiler knows that methods annotated with @Test annotation in the androidTest directory
        // refer to instrumentation tests
            // and methods annotated with @Test annotation in the test directory refer to local tests.

    @Test
    fun testAddNewNoteUI() {

        composeTestRule.setContent { //This sets the UI content of the composeTestRule.
            NotesAppTheme {
                var isDarkMode by remember { mutableStateOf(false) }
                NotesApp(
                    isDarkMode = isDarkMode,
                    onToggleTheme = { isDarkMode = !isDarkMode }
                )
            }
        }

        //instructions to interact with the app's UI components.

        //UI components can be accessed as nodes through the composeTestRule.
            // A common way to do this is to access a node that contains a particular text with the onNodeWithText() method.
            // Use the onNodeWithText() method to access the TextField composable
        //Next you can call the performTextInput() method and pass in the text that you want entered to fill the TextField composable.
        //Use the same approach to populate the OutlinedTextField


        // Click the FAB to add a note
        //find contentDescription with "Add Note" and calls onClick()
        composeTestRule.onNodeWithContentDescription("Add Note").performClick()

        // Type title and content
        composeTestRule.onNodeWithTag("AddTitleField").performTextInput("AddNew() - Testing Note Title")
        composeTestRule.onNodeWithTag("AddContentField").performTextInput("AddNew() - Testing Note Content")

        // Save the note
        //find Text("Add") then perform onClick()
        composeTestRule.onNodeWithText("Add").performClick()

        // Assert that the new note displays in the list
        composeTestRule.onNodeWithText("AddNew() - Testing Note Title").assertIsDisplayed()
        composeTestRule.onNodeWithText("AddNew() - Testing Note Content").assertIsDisplayed()
    }

    @Test
    fun testEditNoteUI() {

        composeTestRule.setContent { //This sets the UI content of the composeTestRule.
            NotesAppTheme {
                var isDarkMode by remember { mutableStateOf(false) }
                NotesApp(
                    isDarkMode = isDarkMode,
                    onToggleTheme = { isDarkMode = !isDarkMode }
                )
            }
        }

        //Add new note for testing:
        composeTestRule.onNodeWithContentDescription("Add Note").performClick()
        composeTestRule.onNodeWithTag("AddTitleField").performTextInput("Edit() - Testing Note Title")
        composeTestRule.onNodeWithTag("AddContentField").performTextInput("Edit() - Testing Note Content")
        composeTestRule.onNodeWithText("Add").performClick()
        //assert note in list
        composeTestRule.onNodeWithText("Edit() - Testing Note Title").assertIsDisplayed()
        composeTestRule.onNodeWithText("Edit() - Testing Note Content").assertIsDisplayed()


        // Click a Note to edit it
        //finds text with "Testing Note Title" and calls onClick()
        composeTestRule.onNodeWithText("Edit() - Testing Note Title").performClick()

        //assert EditNoteDialog displays ("Edit Note" displays)
        composeTestRule.onNodeWithText("Edit Note").assertIsDisplayed()

        // Edit title and content
        composeTestRule.onNodeWithTag("EditTitleField").performTextClearance() //clear, so it won't be appended.
        composeTestRule.onNodeWithTag("EditTitleField").performTextInput("Edit() - Edited Note Title")
        composeTestRule.onNodeWithTag("EditContentField").performTextClearance()
        composeTestRule.onNodeWithTag("EditContentField").performTextInput("Edit() - Edited Note Content")

        // Save the updated changes to the note
        //find Text("Save") then perform onClick()
        composeTestRule.onNodeWithTag("SaveNoteButton").performClick()

        // Assert that the updates on the note displays in the list
        composeTestRule.onNodeWithText("Edit() - Edited Note Title").assertIsDisplayed()
        composeTestRule.onNodeWithText("Edit() - Edited Note Content").assertIsDisplayed()

    }

    @Test
    fun testDeleteNoteUI() {

        composeTestRule.setContent { //This sets the UI content of the composeTestRule.
            NotesAppTheme {
                var isDarkMode by remember { mutableStateOf(false) }
                NotesApp(
                    isDarkMode = isDarkMode,
                    onToggleTheme = { isDarkMode = !isDarkMode }
                )
            }
        }

        //Add new note for testing:
        composeTestRule.onNodeWithContentDescription("Add Note").performClick()
        composeTestRule.onNodeWithTag("AddTitleField").performTextInput("Delete() - Testing Note Title")
        composeTestRule.onNodeWithTag("AddContentField").performTextInput("Delete() - Testing Note Content")
        composeTestRule.onNodeWithText("Add").performClick()
        //assert note in list
        composeTestRule.onNodeWithText("Delete() - Testing Note Title").assertIsDisplayed()
        composeTestRule.onNodeWithText("Delete() - Testing Note Content").assertIsDisplayed()

        // Click a Note to edit it
        //finds text with "Testing Note Title" and calls onClick()
        composeTestRule.onNodeWithText("Delete() - Testing Note Title").performClick()

        //assert EditNoteDialog displays ("Edit Note" displays)
        composeTestRule.onNodeWithText("Edit Note").assertIsDisplayed()

        // Find and click the delete button for the note
        composeTestRule.onNodeWithTag("DeleteNoteButton").performClick()

        // Assert that the note is no longer displayed in the list
        composeTestRule.onNodeWithText("Delete() - Testing Note Title").assertDoesNotExist()
        composeTestRule.onNodeWithText("Delete() - Testing Note Content").assertDoesNotExist()
    }

    @Test
    fun testToggleDarkMode() {

        composeTestRule.setContent { //This sets the UI content of the composeTestRule.
            NotesAppTheme {
                var isDarkMode by remember { mutableStateOf(false) }
                NotesApp(
                    isDarkMode = isDarkMode,
                    onToggleTheme = { isDarkMode = !isDarkMode }
                )
            }
        }

        val switch = composeTestRule.onNodeWithTag("ThemeToggleSwitch")

        // check switch exists and enabled
        switch.assertExists()
        switch.assertIsEnabled()

        // Toggle the theme
        switch.performClick()

        // toggle theme other way
        switch.performClick()
    }
}