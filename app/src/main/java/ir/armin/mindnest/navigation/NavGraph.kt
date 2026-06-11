package ir.armin.mindnest.navigation

import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import ir.armin.mindnest.features.ui.analytics.TrackScreenView
import ir.armin.mindnest.data.analytics.AnalyticsScreens
import ir.armin.mindnest.features.ui.screens.addNoteScreen.AddEditNoteScreen
import ir.armin.mindnest.features.ui.screens.lockScreen.PrivacyLockScreen
import ir.armin.mindnest.features.ui.screens.shownotScreen.NoteListScreen
import ir.armin.mindnest.features.ui.screens.splashScreen.SplashScreen

private const val NOTE_ID_ARG = "noteId"
private val EDIT_NOTE_ROUTE =
    "${Destination.AddNoteScreen.route}/{$NOTE_ID_ARG}"

@Composable
fun AppNavigation(
    startDestination: String = Destination.SplashScreen.route,
    deepLinkDestination: String? = null
) {
    val navController = rememberNavController()

    LaunchedEffect(deepLinkDestination) {
        val destination = deepLinkDestination ?: return@LaunchedEffect
        navController.navigate(destination) {
            launchSingleTop = true
            popUpTo(navController.graph.id) {
                inclusive = false
                saveState = true
            }
            restoreState = true
        }
    }

    NavHost(
        navController = navController,
        startDestination = startDestination
    ) {
        composable(
            route = Destination.SplashScreen.route,
            enterTransition = { fadeIn(tween(500)) },
            exitTransition = { fadeOut(tween(500)) }
        ) {
            TrackScreenView(screenName = AnalyticsScreens.SPLASH)
            SplashScreen(
                onSplashFinished = {
                    navController.navigate(Destination.PrivacyLockScreen.route) {
                        popUpTo(Destination.SplashScreen.route) { inclusive = true }
                    }
                }
            )
        }

        composable(route = Destination.PrivacyLockScreen.route) {
            PrivacyLockScreen(
                onUnlocked = {
                    navController.navigate(Destination.NoteListScreen.route) {
                        popUpTo(Destination.PrivacyLockScreen.route) { inclusive = true }
                    }
                }
            )
        }

        composable(route = Destination.AddNoteScreen.route) {
            AddEditNoteScreen(onNavigateBack = {
                navController.popBackStack()
            })
        }

        composable(
            route = EDIT_NOTE_ROUTE,
            arguments = listOf(navArgument(NOTE_ID_ARG) { type = NavType.IntType })
        ) {
            AddEditNoteScreen(onNavigateBack = {
                navController.popBackStack()
            })
        }

        composable(route = Destination.NoteListScreen.route) {
            val onEditNote: (Int) -> Unit = { noteId ->
                navController.navigate("${Destination.AddNoteScreen.route}/$noteId")
            }

            val onAddNewNote: () -> Unit = {
                navController.navigate(Destination.AddNoteScreen.route)
            }

            NoteListScreen(
                onEditNote = onEditNote,
                onAddNewNote = onAddNewNote
            )
        }
    }
}
