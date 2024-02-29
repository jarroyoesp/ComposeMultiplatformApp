plugins {
    id("composeapp.multiplatform-library-conventions")
    id("org.jetbrains.compose")
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
            implementation(libs.tlaster.precompose.viewmodel)
        }
    }
}