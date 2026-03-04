plugins {
    id("composeapp.multiplatform-library-conventions")
    alias(libs.plugins.kotlinx.serialization)
}


kotlin {
    android {
        namespace = "com.jarroyo.library.network"
        //resourcePrefix = "network_"
        //defaultConfig {
        //    consumerProguardFiles("$projectDir/proguard-network-consumer-rules.pro")
        //}
    }
    sourceSets {
        commonMain.dependencies {
            api(projects.modules.libraryNetworkApi)
            implementation(libs.koin.core)
            implementation(libs.ktor.client.content.negotiation)
            implementation(libs.ktor.client.core)
            implementation(libs.ktor.client.cio)
            implementation(libs.ktor.serialization.kotlinx.json)
        }

        iosMain.dependencies {
            implementation(libs.ktor.client.darwin)
        }

        desktopMain.dependencies {
            implementation(libs.ktor.client.java)
        }

    }
}
