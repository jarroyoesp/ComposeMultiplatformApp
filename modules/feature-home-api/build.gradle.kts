plugins {
    id("composeapp.multiplatform-library-conventions")
    alias(libs.plugins.kotlinx.serialization)
}

kotlin {
    android {
        namespace = "com.jarroyo.feature.home.api"
    }

    sourceSets {
        commonMain.dependencies {
            api(libs.jetbrains.kotlinx.serialization)

            implementation(projects.modules.libraryNavigationApi)
            implementation(projects.modules.libraryNetworkApi)
        }
    }
}