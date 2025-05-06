plugins {
    id("composeapp.multiplatform-library-conventions")
    alias(libs.plugins.kotlinx.serialization)
}

android {
    namespace = "com.jarroyo.feature.electricity.api"
    resourcePrefix = "electricity_api_"
    defaultConfig {
        consumerProguardFiles("$projectDir/proguard-home-api-consumer-rules.pro")
    }
}

kotlin {
    sourceSets {
        commonMain.dependencies {
            api(libs.kotlinx.serialization)

            implementation(projects.modules.libraryNavigationApi)
            implementation(projects.modules.libraryNetworkApi)
            implementation(projects.modules.libraryNetwork)
        }
    }
}