plugins {
    id("composeapp.multiplatform-library-conventions")
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
            implementation(libs.koin.core)
            implementation(projects.modules.featureHomeApi)
        }
    }
}