plugins {
    id("composeapp.multiplatform-library-conventions")
    alias(libs.plugins.kotlinx.serialization)
}

android {
    namespace = "com.jarroyo.library.network"
    resourcePrefix = "network_"
    defaultConfig {
        consumerProguardFiles("$projectDir/proguard-network-consumer-rules.pro")
    }
    sourceSets {
        val main by getting
        main.manifest.srcFile("src/androidMain/AndroidManifest.xml")
    }
}

kotlin {
    sourceSets {
        commonMain.dependencies {
            api(projects.modules.libraryNetworkApi)
            implementation(libs.koin.core)
        }
    }
}