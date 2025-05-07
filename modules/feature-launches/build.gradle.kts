plugins {
    id("composeapp.multiplatform-feature-conventions")
    id("org.jetbrains.compose")
    id("org.jetbrains.kotlin.plugin.compose")
    alias(libs.plugins.sqldelight)
}

android {
    namespace = "com.jarroyo.launches.schedules"
    sourceSets["main"].apply {
        res.srcDirs("src/androidMain/res", "src/commonMain/resources")
    }
}

sqldelight {
    databases {
        create("Database") {
            packageName.set("com.jarroyo.feature.launches.sqldelight")
        }
    }
    linkSqlite.set(true)
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
            implementation(libs.sqldelight.androidDriver)
        }
        commonMain.dependencies {
            implementation(compose.ui)
            implementation(compose.foundation)
            implementation(compose.material)
            implementation(compose.runtime)
            implementation(libs.jetbrains.kotlin.datetime)
            implementation(libs.sqldelight.coroutines)

            implementation(projects.modules.featureCommonApi)
            implementation(projects.modules.featureLaunchesApi)
            implementation(projects.modules.libraryNavigation)
            implementation(projects.modules.libraryNetworkApi)
            implementation(projects.modules.libraryNetwork)
        }

        desktopMain.dependencies {
            implementation(libs.sqldelight.jvmDriver)
        }

        iosMain.dependencies {
            implementation(libs.sqldelight.nativeDriver)
        }
    }
}