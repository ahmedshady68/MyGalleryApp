# MyGalleryApp
a simple app which shows list of Images and Videos like native Gallery App.

Android Kotlin Application that build in clean architecture and best practises

## summary
In this Application we have 4 screen with 3 screens for 3 Tabs in a NavigationBar and 1 details screen for Albums content.

All images First tab : that display a list of all images and can scroll between them.
All videos second tab : that display a list of all videos and can scroll between them.
All albums third tab : that display a list of albums and can scroll between them.
album contents screen : that display a list of items in one album and can scroll between them.

In this branch you'll find:
*   User Interface built with **[Jetpack Compose](https://developer.android.com/jetpack/compose)**
*   A presentation layer that contains a Compose screen (View) and a **ViewModel** per screen (or feature).
*   Reactive UIs using **[Flow](https://developer.android.com/kotlin/flow)** and **[coroutines](https://kotlinlang.org/docs/coroutines-overview.html)** for asynchronous operations.
*   Dependency injection using [Hilt](https://developer.android.com/training/dependency-injection/hilt-android).