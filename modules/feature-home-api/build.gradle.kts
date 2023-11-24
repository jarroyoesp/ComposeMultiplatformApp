plugins {
    id ("composeapp.multiplatform-library-conventions")
}

android {
    namespace = "com.jarroyo.feature.home.api"
    resourcePrefix = "home_api_"
    defaultConfig {
        consumerProguardFiles ("$projectDir/proguard-home-api-consumer-rules.pro")
    }
}

kotlin {
    jvm("desktop")
    androidTarget()

    sourceSets {
        val commonMain by getting{
            dependencies{
                implementation (libs.kotlin.result)
                implementation (projects.modules.libraryNetworkApi)
            }
        }
    }
}