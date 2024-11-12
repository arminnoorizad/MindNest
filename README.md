  purpleNote is an Android note-taking application built with a focus on clean architecture using MVVM (Model-View-ViewModel) and Android's Jetpack components. The app allows users to create, edit, and delete notes, featuring a persistent local storage solution with Room database, asynchronous data operations with Coroutines, and reactive UI updates using LiveData.

## Features
- **MVVM Architecture**: Ensures a clean separation of concerns, making the app maintainable and testable.
- **Room Database**: Stores notes locally on the device with support for data persistence.
- **Coroutines**: Executes database operations asynchronously for smooth user interactions.
- **LiveData**: Automatically updates the UI when data changes in the database.
- **Jetpack Compose (optional)**: If Compose is used, for declarative UI components and a modern design.
- **Image and Voice Note Support**: Allows users to add images and record voice notes to their entries.
