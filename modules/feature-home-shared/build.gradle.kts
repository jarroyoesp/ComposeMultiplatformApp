plugins {
    id("composeapp.multiplatform-library-conventions")
    id("org.jetbrains.compose")
}

android {
    namespace = "com.jarroyo.feature.home.shared"
    sourceSets["main"].apply {
        res.srcDirs("src/androidMain/res", "src/commonMain/resources")
    }
}

kotlin {
    sourceSets {
        androidMain.dependencies {
            implementation(libs.androidx.lifecycle.viewmodel)
            implementation(libs.ktor.client.android)
        }
        commonMain.dependencies {
            implementation(compose.ui)
            implementation(compose.foundation)
            implementation(compose.material)
            implementation(compose.runtime)
            implementation(libs.koin.core)
            implementation(libs.coroutines.core)
            implementation(libs.kamel)
            implementation(libs.multiplatform.log)
            implementation(libs.tlaster.precompose)
            implementation(libs.tlaster.precompose.viewmodel)
            implementation(projects.modules.featureHomeApi)
            implementation(projects.modules.libraryNavigationApi)
            implementation(projects.modules.libraryNavigation)
            implementation(projects.modules.libraryNetworkApi)
            implementation(projects.modules.libraryNetwork)
            implementation(projects.modules.libraryUiShared)
        }

        desktopMain.dependencies {
            implementation(libs.coroutines.core)
            implementation(libs.ktor.client.cio)
        }
    }
}