package com.example.voicesofheart.ui.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.voicesofheart.ui.home.HomeScreen
import com.example.voicesofheart.ui.library.LibraryScreen
import com.example.voicesofheart.ui.kaizen.KaizenScreen
import com.example.voicesofheart.ui.folders.FoldersScreen

@Composable
fun NavGraph(navController: NavHostController) {
    NavHost(navController = navController, startDestination = Screen.Home.route) {
        composable(Screen.Home.route) { HomeScreen() }
        composable(Screen.Library.route) { LibraryScreen() }
        composable(Screen.Kaizen.route) { KaizenScreen() }
        composable(Screen.Folders.route) { FoldersScreen() }
    }
}