# Compose Multiplatform App
This project is a Kotlin Multiplatform application that consumes a GraphQL endpoint to retrieve details about rocket launches conducted by SpaceX. It utilizes several libraries including Compose Multiplatform for UI, SQLDelight for local data storage, and Apollo GraphQL for fetching data from the server.

# Features
> Displays details of rocket launches conducted by SpaceX.
> Retrieves data from a GraphQL endpoint.
> Stores launch details locally using SQLDelight for offline access.
> Provides a user-friendly UI built with Compose Multiplatform.

# Libraries Used
> Kotlin Multiplatform: Kotlin language support for targeting multiple platforms.
> Compose Multiplatform: [https://github.com/JetBrains/compose-multiplatform] Modern UI framework for building native interfaces across different platforms.
> SQLDelight: [https://github.com/cashapp/sqldelight]
> Apollo GraphQL [https://github.com/apollographql].
> Navigation: PreCompose [https://github.com/Tlaster/PreCompose/blob/master/docs/component/navigation.md]
> ViewModel: PreCompose ViewModel [https://github.com/Tlaster/PreCompose/blob/master/docs/component/view_model.md]
> ConstraintLayout Multiplatform: [https://github.com/Lavmee/constraintlayout-compose-multiplatform]
> Image Loader - Kamel: [https://github.com/Kamel-Media/Kamel]
> Logger: [https://github.com/touchlab/Kermit]
> Unit tests


# Android

# Desktop

# iOS
https://www.jetbrains.com/help/kotlin-multiplatform-dev/multiplatform-integrate-in-existing-app.html#make-your-cross-platform-application-work-on-ios

Add to your `Build phases`
```
cd "$SRCROOT/.."
./gradlew :modules:feature-home-shared:embedAndSignAppleFrameworkForXcode
```

Search Paths:
```
$(SRCROOT)/../modules/feature-home-shared/build/xcode-frameworks/$(CONFIGURATION)/$(SDK_NAME)
```
