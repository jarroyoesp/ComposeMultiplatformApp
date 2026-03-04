plugins {
    id("composeapp.multiplatform-library-conventions")
}


kotlin {
android {
    namespace = "com.jarroyo.library.navigation.api"
    // resourcePrefix = "navigation_api_"
    // defaultConfig {
    //     consumerProguardFiles("$projectDir/proguard-navigation-api-consumer-rules.pro")
    // }
}
    sourceSets {
        commonMain.dependencies {
            implementation(libs.coroutines.core)
        }
    }
}