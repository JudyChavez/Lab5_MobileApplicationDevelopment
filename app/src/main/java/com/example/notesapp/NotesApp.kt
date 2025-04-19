package com.example.notesapp

import android.R.string
import androidx.compose.material.icons.Icons.Filled
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarScrollBehavior
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.notesapp.ui.home.HomeScreen

/**
 * Top level composable that represents screens for the application.
 */
@Composable
fun NotesApp(
    //navController: NavHostController = rememberNavController()
) {
    //InventoryNavHost(navController = navController)

    HomeScreen()

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
    navigateUp: () -> Unit = {}
) {
    CenterAlignedTopAppBar(
        title = { Text(title) },
        modifier = modifier,
        scrollBehavior = scrollBehavior,
//        navigationIcon = {
//            if (canNavigateBack) {
//                IconButton(onClick = navigateUp) {
//                    Icon(
//                        imageVector = Filled.ArrowBack,
//                        contentDescription = stringResource(R.string.back_button)
//                    )
//                }
//            }
//        }
    )
}