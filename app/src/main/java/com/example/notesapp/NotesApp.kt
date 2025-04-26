package com.example.notesapp

import androidx.compose.material.icons.Icons.Filled
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarScrollBehavior
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.stringResource
import com.example.notesapp.ui.home.HomeScreen

/**
 * Top level composable that represents screens for the application.
 */
@Composable
fun NotesApp(
    isDarkMode: Boolean,
    onToggleTheme: () -> Unit
) {
    MaterialTheme(
        colorScheme = if (isDarkMode) darkColorScheme() else lightColorScheme()
    ) {
        HomeScreen(
            onToggleTheme = onToggleTheme,//{ isDarkMode = !isDarkMode },
            isDarkMode = isDarkMode
        )
    }
}

/**
 * App bar to display title and conditionally display the back navigation.
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NotesTopAppBar(
    title: String,
    canNavigateBack: Boolean,
    modifier: Modifier = Modifier,
    scrollBehavior: TopAppBarScrollBehavior? = null,
    onThemeToggle: () -> Unit,
    navigateUp: () -> Unit = {},
    isDarkMode: Boolean
) {
    /*CenterAligned*/TopAppBar(
        title = { Text(stringResource(R.string.my_notes)) },
        modifier = modifier,
        scrollBehavior = scrollBehavior,
        navigationIcon = {
            if (canNavigateBack) {
                IconButton(onClick = navigateUp) {
                    Icon(
                        imageVector = Filled.ArrowBack,
                        contentDescription = stringResource(R.string.back_button)
                    )
                }
            }
        },
        actions = {
            Switch(
                checked = isDarkMode,//isSystemInDarkTheme(), // Initially reflect the system theme
                onCheckedChange = { onThemeToggle() },
                modifier = Modifier.testTag("ThemeToggleSwitch")
            )
        }
    )
}

