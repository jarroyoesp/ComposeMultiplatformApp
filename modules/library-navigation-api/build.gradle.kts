plugins {
    id ("composeapp.android-library-conventions")
}

android {
    namespace = "com.jarroyo.library.navigation.api"
    resourcePrefix = "navigation_api_"
    defaultConfig {
        consumerProguardFiles ("$projectDir/proguard-navigation-api-consumer-rules.pro")
    }
}

dependencies {
    api (libs.androidx.navigation.compose)
}
