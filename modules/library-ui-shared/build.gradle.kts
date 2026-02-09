plugins {
    id("composeapp.multiplatform-library-conventions")
    id("org.jetbrains.compose")
    id("org.jetbrains.kotlin.plugin.compose")
}

android {
    namespace = "com.jarroyo.library.ui.shared"
}

kotlin {
    sourceSets {
        sourceSets {
            commonMain {
                resources.srcDirs("src/commonMain/resources")
            }
        }

        commonMain.dependencies {
            api(libs.jetbrains.compose.material.icons.extended)
            implementation(libs.jetbrains.kotlinx.serialization)
            implementation(projects.modules.libraryNavigationApi)

            implementation(libs.jetbrains.compose.components.resources)
            implementation(libs.jetbrains.compose.foundation)
            implementation(libs.jetbrains.compose.material)
            implementation(libs.jetbrains.compose.material3)
            implementation(libs.jetbrains.compose.ui)
            implementation(libs.jetbrains.compose.ui.graphics)
            implementation(libs.compose.placeholder)
            implementation(libs.coroutines.core)
        }
    }
}