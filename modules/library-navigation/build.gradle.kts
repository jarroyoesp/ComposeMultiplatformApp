plugins {
    id("composeapp.multiplatform-library-conventions")
    // id ("com.google.dagger.hilt.android")
}

android {
    namespace = "com.jarroyo.library.navigation"
    resourcePrefix = "navigation_"
    defaultConfig {
        consumerProguardFiles("$projectDir/proguard-navigation-consumer-rules.pro")
    }
}

kotlin {
    sourceSets {
        commonMain.dependencies {
            api(projects.modules.libraryNavigationApi)
            implementation(libs.tlaster.precompose)
            implementation(libs.koin.core)
        }
    }
}