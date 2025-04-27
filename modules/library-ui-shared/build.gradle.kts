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
            implementation(compose.components.resources)
            implementation(compose.foundation)
            implementation(compose.material)
            implementation(libs.androidx.compose.material3)
            implementation(libs.androidx.compose.ui)
            implementation(libs.androidx.compose.ui.graphics)
            implementation(libs.androidx.compose.ui.resources)
            implementation(libs.compose.placeholder)
            implementation(libs.coroutines.core)
        }
    }
}