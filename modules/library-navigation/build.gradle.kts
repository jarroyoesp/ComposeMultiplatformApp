plugins {
    id("composeapp.multiplatform-library-conventions")
}

kotlin {
    android {
      namespace = "com.jarroyo.library.navigation"
    }

    sourceSets {
        commonMain.dependencies {
            api(projects.modules.libraryNavigationApi)
            implementation(libs.koin.core)
            implementation(projects.modules.featureLaunchesApi)
            implementation(projects.modules.featureLoginApi)
        }
    }
}