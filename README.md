# Mind Nest

<img width="1920" height="1536" alt="Mind Nest preview" src="https://github.com/user-attachments/assets/609ce9c6-148f-4e84-8ca3-6914bc6f49b9" />

Mind Nest is a modern multimedia note-taking app for Android, built with Jetpack Compose, Clean Architecture, Hilt, Room, SQLCipher encryption, and Firebase services. It focuses on a smooth offline-first writing experience for private notes, images, and voice memories.

---

## Features

- **Compose UI:** Minimal Material 3 interface built with Jetpack Compose.
- **Rich notes:** Text notes with image attachments, background selection, and voice notes.
- **Private storage:** Room database protected with SQLCipher and a locally managed encrypted passphrase.
- **Push notifications:** Firebase Cloud Messaging integration for foreground and background notification events.
- **Analytics and crash reporting:** Firebase Analytics and Crashlytics integration.
- **Privacy lock UI:** A dedicated lock screen is prepared as the entry point for future biometric authentication.

---

## Tech Stack

- **Language:** Kotlin
- **UI:** Jetpack Compose, Material 3
- **Architecture:** MVVM + Clean Architecture
- **Dependency injection:** Hilt / Dagger
- **Database:** Room + SQLCipher
- **Async:** Kotlin Coroutines and Flow
- **Images:** Coil
- **Firebase:** Messaging, Analytics, Crashlytics
- **Testing:** JUnit, Espresso, Compose UI tests

---

## Architecture

The codebase is split into clear layers:

1. **Presentation:** Compose screens, ViewModels, UI state, and navigation.
2. **Domain:** Use cases such as `ProcessFcmMessageUseCase`.
3. **Data:** Repositories, local database, Firebase integrations, and platform services.
4. **DI:** Hilt modules for wiring repositories, database, analytics, and audio components.

Useful entry points:

- `app/src/main/java/ir/armin/mindnest/di/App.kt`
- `app/src/main/java/ir/armin/mindnest/di/modules/DatabaseModule.kt`
- `app/src/main/java/ir/armin/mindnest/data/local/database/DatabasePassphraseManager.kt`
- `app/src/main/java/ir/armin/mindnest/features/ui/MainActivity.kt`
- `app/src/main/java/ir/armin/mindnest/navigation/NavGraph.kt`

---

## Getting Started

### Prerequisites

- Android Studio
- JDK 17 or the bundled Android Studio JBR
- Android SDK
- Firebase project configuration for package `ir.armin.mindnest`

### Build

PowerShell:

```powershell
Set-Location -Path "C:\Users\armin\AndroidStudioProjects\MindNest2"
$env:JAVA_HOME = "C:\Users\armin\AppData\Local\Programs\Android Studio\jbr"
$env:PATH = "$env:JAVA_HOME\bin;$env:PATH"
.\gradlew.bat assembleDebug
```

### Firebase

Place `google-services.json` inside the `app/` folder for local development. Do not commit Firebase config files, keystores, passwords, or release artifacts to a public repository.

---

## Security Notes

- The database passphrase is stored through `EncryptedSharedPreferences`.
- Note media files are currently stored in app-private storage.
- For stronger attachment protection, migrate sensitive media to `EncryptedFile`.
- Before publishing the repository, verify that `google-services.json`, keystores, and signing passwords are not present in Git history.
- Release APK/AAB outputs are ignored through `.gitignore`.

---

## Useful Commands

```powershell
.\gradlew.bat assembleDebug
.\gradlew.bat test
.\gradlew.bat lint
```

---

## Next Steps

- Wire the privacy lock UI to AndroidX Biometric.
- Add encrypted export options for notes.
- Add voice-to-text transcription for voice notes.
- Add CI for build, lint, and unit tests.
