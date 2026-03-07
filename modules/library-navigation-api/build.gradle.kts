plugins {
    id("composeapp.multiplatform-library-conventions")
}


kotlin {
    android {
        namespace = "com.jarroyo.library.navigation.api"
    }
    sourceSets {
        commonMain.dependencies {
            implementation(libs.coroutines.core)
        }
    }
}