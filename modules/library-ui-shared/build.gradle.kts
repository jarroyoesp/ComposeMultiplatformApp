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
        commonMain.dependencies {
            implementation(compose.foundation)
            implementation(libs.compose.placeholder)
            implementation(libs.coroutines.core)
        }
    }
}