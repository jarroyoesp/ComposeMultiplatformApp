plugins {
    id("composeapp.multiplatform-library-conventions")
}

android {
    namespace = "com.jarroyo.library.navigation.api"
    resourcePrefix = "navigation_api_"
    defaultConfig {
        consumerProguardFiles("$projectDir/proguard-navigation-api-consumer-rules.pro")
    }
}

kotlin {
    sourceSets {
        commonMain.dependencies {
            implementation(libs.coroutines.core)
        }
    }
}