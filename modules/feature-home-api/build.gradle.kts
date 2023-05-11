plugins {
    id ("composeapp.android-library-conventions")
}

android {
    namespace = "com.jarroyo.feature.home.api"
    resourcePrefix = "home_api_"
    defaultConfig {
        consumerProguardFiles ("$projectDir/proguard-home-api-consumer-rules.pro")
    }
}

dependencies {
    implementation (projects.modules.libraryNetworkApi)
}