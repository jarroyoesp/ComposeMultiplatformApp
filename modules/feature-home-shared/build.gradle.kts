plugins {
    id ("composeapp.multiplatform-library-conventions")
    id("org.jetbrains.compose")
}

android {
    namespace = "com.jarroyo.feature.home.shared"
    sourceSets["main"].apply {
        res.srcDirs("src/androidMain/res", "src/commonMain/resources")
    }
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
                implementation(compose.ui)
                implementation(compose.foundation)
                implementation(compose.material)
                implementation(compose.runtime)
                implementation(libs.koin.core)
                implementation(libs.coroutines.core)
                implementation(libs.multiplatform.log)
                implementation(libs.tlaster.precompose)
                implementation(projects.modules.featureHomeApi)
                implementation(projects.modules.libraryNavigationApi)
                implementation(projects.modules.libraryNavigation)
                implementation(projects.modules.libraryNetworkApi)
                implementation(projects.modules.libraryNetwork)
                implementation(projects.modules.libraryUiShared)
            }
        }
        val desktopMain by getting {
            dependencies {
                implementation(libs.coroutines.core)
            }
        }
    }
}