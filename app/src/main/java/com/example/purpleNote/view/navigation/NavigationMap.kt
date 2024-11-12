package com.example.purpleNote.view.navigation

import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.scaleIn
import androidx.compose.animation.scaleOut
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.purpleNote.view.ui.AddNoteScreen
import com.example.purpleNote.view.ui.ShowNoteScreen

object Destinations {
    const val ShowNoteScreen = "ShowNoteScreen"
    const val AddNoteScreen = "AddNoteScreen?noteId={noteId}"

    fun addNoteScreen(noteId: Int? = null): String {
        return if (noteId != null) {
            "AddNoteScreen?noteId=$noteId"
        } else {
            "AddNoteScreen"
        }
    }
}
@Composable
fun Nav() {
    val navController = rememberNavController()
    NavHost(
        navController = navController,
        startDestination = Destinations.ShowNoteScreen
    ) {
        composable(
            Destinations.ShowNoteScreen,
            enterTransition = {
                scaleIn(initialScale = 0.8f, animationSpec = tween(4000)) + fadeIn(tween(500))
            },
            exitTransition = {
                scaleOut(targetScale = 0.8f, animationSpec = tween(4000)) + fadeOut(tween(500))
            }
        ) {
            ShowNoteScreen(navController)
        }

        composable(
            route = Destinations.AddNoteScreen,
            arguments = listOf(navArgument("noteId") { nullable = true }),
            enterTransition = {

                scaleIn(initialScale = 0.8f, animationSpec = tween(4000)) + slideInHorizontally(initialOffsetX = { it }) + fadeIn(tween(500))
            },
            exitTransition = {

                scaleOut(targetScale = 0.8f, animationSpec = tween(4000)) + slideOutHorizontally(targetOffsetX = { -it }) + fadeOut(tween(500))
            }
        ) { backStackEntry ->
            val noteId = backStackEntry.arguments?.getString("noteId")?.toIntOrNull()
            AddNoteScreen(navController, noteId)
        }
    }
}