plugins {
    id("composeapp.multiplatform-library-conventions")
}

kotlin {
    android {
      namespace = "com.jarroyo.library.navigation"
      // resourcePrefix = "navigation_"
      // defaultConfig {
      //   consumerProguardFiles("$projectDir/proguard-navigation-consumer-rules.pro")
      // }
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