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
            api(compose.materialIconsExtended)
            implementation(libs.jetbrains.kotlinx.serialization)
            implementation(projects.modules.libraryNavigationApi)

            implementation(compose.components.resources)
            implementation(compose.foundation)
            implementation(compose.material)
            implementation(libs.jetbrains.compose.material3)
            implementation(libs.jetbrains.compose.ui)
            implementation(libs.jetbrains.compose.ui.graphics)
            implementation(libs.jetbrains.compose.ui.resources)
            implementation(libs.compose.placeholder)
            implementation(libs.coroutines.core)
        }
    }
}