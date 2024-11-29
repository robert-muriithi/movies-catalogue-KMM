# Screenshots
<img src="ss/1730923811879.jpeg" width="1000"/>
<img src="ss/1730923816155.jpeg" width="1000"/>
<img src="ss/1730923817348.jpeg" width="1000"/>

# Key Highlights
1. **Paging3** for smooth, efficient movie loading from TMDB API 
2. **Ktor** for robust networking 
3. **Room DB** to store favorite movies offline 
4. **Shared Element Transition** for an enhanced user experience, allowing smoother visual transitions between screens!
5. **DataStore** Preferences

This is a Kotlin Multiplatform project targeting Android, iOS.

* `/composeApp` is for code that will be shared across your Compose Multiplatform applications.
  It contains several subfolders:
  - `commonMain` is for code that’s common for all targets.
  - Other folders are for Kotlin code that will be compiled for only the platform indicated in the folder name.
    For example, if you want to use Apple’s CoreCrypto for the iOS part of your Kotlin app,
    `iosMain` would be the right folder for such calls.

* `/iosApp` contains iOS applications. Even if you’re sharing your UI with Compose Multiplatform, 
  you need this entry point for your iOS app. This is also where you should add SwiftUI code for your project.

Learn more about [Kotlin Multiplatform](https://www.jetbrains.com/help/kotlin-multiplatform-dev/get-started.html)…
