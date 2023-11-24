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
    jvm("desktop")
    androidTarget()

    sourceSets {
        val commonMain by getting {
            dependencies {
                implementation(libs.coroutines.core)
                implementation(libs.tlaster.precompose)
                // api (libs.androidx.navigation.compose)
            }
        }
    }
}