plugins {
    id("composeapp.multiplatform-library-conventions")
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
            implementation(projects.modules.libraryNavigationApi)
            implementation(projects.modules.libraryNetworkApi)
        }
    }
}