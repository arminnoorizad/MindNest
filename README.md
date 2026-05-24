# Mind Nest 🧠

A premium, modern, and minimalist multimedia note-taking application for Android. Built from the ground up using **Jetpack Compose (Material 3)** and **Clean Architecture** principles, **Mind Nest** provides a seamless, secure, and intuitive experience for organizing your thoughts.



## ✨ Features

- **Premium UI/UX:** Stunning and minimalist interface powered completely by **Jetpack Compose** and Material Design 3 guidelines.
- **Rich Multimedia Notes:** - 🎙️ **Voice Notes:** Integrated audio recording and custom-designed inline audio playback controller (`VoiceNotePlayer`).
  - 🖼️ **Image Attachments:** Smooth image loading and clean attachment grids.
- **Smart Push Notifications:** Fully integrated with **Firebase Cloud Messaging (FCM)** for real-time background and foreground notification events.
- **Privacy-First Analytics:** Customized Google Analytics architecture with manual control over automatic screen view reporting (`google_analytics_automatic_screen_reporting_enabled = false`) for maximum user data protection.
- **Robust Error Handling:** Architecture built around a unified `AppResult` state wrapper for resilient data flows.

---

## 🛠️ Tech Stack & Architecture

This repository showcases production-ready Android development practices, emphasizing the **SOLID Principles**:

- **Language:** 100% Kotlin
- **UI Framework:** Jetpack Compose (Material 3, Custom Modifiers, State Hoisting)
- **Dependency Injection:** Hilt / Dagger (Clean Architecture scoped modules)
- **Async & Reactive Programming:** Kotlin Coroutines & Flow (StateFlow / SharedFlow)
- **Push Notifications:** Firebase Cloud Messaging (FCM)
- **Local Audio Engine:** Jetpack Media3 / Android Lifecycle-aware Audio Player
- **Testing:** Local JUnit 4 and UI Espresso/Compose Instrumentation Tests

---

## 🏗️ Architectural Layers

The codebase strictly decouples concerns across three distinct layers:
1. **Presentation:** State-driven MVI/MVVM pattern utilizing Jetpack ViewModels and Compose states.
2. **Domain:** Pure business logic and decoupled Use Cases (e.g., `ProcessFcmMessageUseCase`).
3. **Data:** Repository implementations coordinating local caching, data sources, and analytic tracking dispatchers.

---

## 🚀 Getting Started

### Prerequisites
- Android Studio Jellyfish (or newer)
- Android SDK 34+
- A Firebase project configuration (place your `google-services.json` inside the `app/` folder)

### Installation
1. Clone the repository:
   ```bash
   git clone [https://github.com/your-username/mind-nest.git](https://github.com/your-username/mind-nest.git)
