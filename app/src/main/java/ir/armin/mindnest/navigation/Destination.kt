package ir.armin.mindnest.navigation

sealed class Destination(val route: String) {
    data object SplashScreen : Destination("SplashScreen")
    data object AddNoteScreen : Destination("add_note_screen")
    data object NoteListScreen : Destination("NoteListScreen")

}


