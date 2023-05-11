plugins {
    id ("composeapp.android-library-conventions")
    id ("com.google.dagger.hilt.android")
}

android {
    namespace = "com.jarroyo.library.navigation"
    resourcePrefix = "navigation_"
    defaultConfig {
        consumerProguardFiles ("$projectDir/proguard-navigation-consumer-rules.pro")
    }
}

dependencies {
    api (projects.modules.libraryNavigationApi)

    implementation (libs.hilt.android)
    implementation (libs.hilt.navigation.compose)
    kapt (libs.hilt.compiler)
}