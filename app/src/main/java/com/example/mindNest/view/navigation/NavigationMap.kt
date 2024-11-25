package com.example.mindNest.view.navigation

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
import com.example.mindNest.view.ui.AddNoteScreen
import com.example.mindNest.view.ui.ShowNoteScreen
import com.example.mindNest.view.ui.SplashScreen

object Destinations {
    const val SplashScreen = "SplashScreen"
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
        startDestination = Destinations.SplashScreen
    ) {
        // Splash Screen Route
        composable(
            Destinations.SplashScreen,
            enterTransition = {
                fadeIn(tween(500))
            },
            exitTransition = {
                fadeOut(tween(500))
            }
        ) {
            SplashScreen(
                onSplashFinished = {
                    navController.navigate(Destinations.ShowNoteScreen) {
                        popUpTo(Destinations.SplashScreen) { inclusive = true }
                    }
                }
            )
        }

        // Show Note Screen Route
        composable(
            Destinations.ShowNoteScreen,
            enterTransition = {
                scaleIn(initialScale = 0.8f, animationSpec = tween(3000)) + fadeIn(tween(500))
            },
            exitTransition = {
                scaleOut(targetScale = 0.8f, animationSpec = tween(3000)) + fadeOut(tween(500))
            }
        ) {
            ShowNoteScreen(navController)
        }

        // Add Note Screen Route
        composable(
            route = Destinations.AddNoteScreen,
            arguments = listOf(navArgument("noteId") { nullable = true }),
            enterTransition = {
                scaleIn(initialScale = 0.8f, animationSpec = tween(3000)) +
                        slideInHorizontally(initialOffsetX = { it }) +
                        fadeIn(tween(500))
            },
            exitTransition = {
                scaleOut(targetScale = 0.8f, animationSpec = tween(3000)) +
                        slideOutHorizontally(targetOffsetX = { -it }) +
                        fadeOut(tween(500))
            }
        ) { backStackEntry ->
            val noteId = backStackEntry.arguments?.getString("noteId")?.toIntOrNull()
            AddNoteScreen(navController, noteId)
        }
    }
}