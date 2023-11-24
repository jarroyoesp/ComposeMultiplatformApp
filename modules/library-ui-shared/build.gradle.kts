plugins {
    id ("composeapp.multiplatform-library-conventions")
    id("org.jetbrains.compose")
}

android {
    namespace = "com.jarroyo.library.ui.shared"
}

kotlin {
    jvm("desktop")
    androidTarget()

    sourceSets {
        val androidMain by getting {
            dependencies {
                implementation(libs.androidx.lifecycle.viewmodel)
            }
        }
        val commonMain by getting{
            dependencies{
                implementation(compose.foundation)
                implementation(libs.coroutines.core)
            }
        }
    }
}