plugins {
    id("composeapp.multiplatform-feature-conventions")
    id("org.jetbrains.compose")
    id("org.jetbrains.kotlin.plugin.compose")
}

android {
    namespace = "com.jarroyo.feature.schedules"
    sourceSets["main"].apply {
        res.srcDirs("src/androidMain/res", "src/commonMain/resources")
    }
}

kotlin {
    // REVIEW reason of ld: framework 'FirebaseCore' not found
    tasks.matching { it.name == "linkDebugTestIosSimulatorArm64" }.configureEach {
        enabled = false
    }
    tasks.matching { it.name == "linkDebugTestIosX64" }.configureEach {
        enabled = false
    }
    sourceSets {
        androidMain.dependencies {
        }
        commonMain.dependencies {
            implementation(libs.jetbrains.compose.components.resources)
            implementation(libs.jetbrains.compose.ui)
            implementation(libs.jetbrains.compose.foundation)
            implementation(libs.jetbrains.compose.material)
            implementation(libs.jetbrains.compose.runtime)
            implementation(libs.sqldelight.coroutines)

            implementation(projects.modules.featureAccountApi)
            implementation(projects.modules.featureLoginApi)
            implementation(projects.modules.featureSchedulesApi)
            implementation(projects.modules.libraryNavigation)
            implementation(projects.modules.libraryNetworkApi)
            implementation(projects.modules.libraryNetwork)
        }

        desktopMain.dependencies {

        }

        iosMain.dependencies {
        }
    }
}