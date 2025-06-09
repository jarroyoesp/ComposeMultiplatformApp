plugins {
    id("composeapp.multiplatform-feature-conventions")
    id("org.jetbrains.compose")
    id("org.jetbrains.kotlin.plugin.compose")
}

android {
    namespace = "com.jarroyo.login"
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
            implementation(compose.ui)
            implementation(compose.foundation)
            implementation(compose.material)
            implementation(compose.runtime)

            implementation(projects.modules.featureAccountApi)
            implementation(projects.modules.featureCommonApi)
            implementation(projects.modules.featureLoginApi)
            implementation(projects.modules.libraryNavigation)
            implementation(projects.modules.libraryNetworkApi)
            implementation(projects.modules.libraryNetwork)

            implementation(libs.gitlive.firebase.auth)
        }

        desktopMain.dependencies {
        }

        iosMain.dependencies {
        }
    }
}