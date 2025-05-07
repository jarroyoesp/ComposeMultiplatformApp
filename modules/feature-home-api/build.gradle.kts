plugins {
    id("composeapp.multiplatform-library-conventions")
    alias(libs.plugins.kotlinx.serialization)
}

android {
    namespace = "com.jarroyo.feature.home.api"
    resourcePrefix = "home_api_"
    defaultConfig {
        consumerProguardFiles("$projectDir/proguard-home-api-consumer-rules.pro")
    }
}

kotlin {
    sourceSets {
        commonMain.dependencies {
            api(libs.jetbrains.kotlinx.serialization)

            implementation(projects.modules.libraryNavigationApi)
            implementation(projects.modules.libraryNetworkApi)
        }
    }
}