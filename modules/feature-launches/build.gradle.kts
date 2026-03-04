plugins {
    id("composeapp.multiplatform-feature-conventions")
    id("org.jetbrains.compose")
    id("org.jetbrains.kotlin.plugin.compose")
    alias(libs.plugins.sqldelight)
}

kotlin {
    android {
        namespace = "com.jarroyo.launches.schedules"
    }

    sourceSets {
        androidMain.dependencies {
            implementation(libs.sqldelight.androidDriver)
        }
        commonMain.dependencies {
            implementation(libs.jetbrains.compose.ui)
            implementation(libs.jetbrains.compose.foundation)
            implementation(libs.jetbrains.compose.material)
            implementation(libs.jetbrains.compose.runtime)
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

sqldelight {
    databases {
        create("Database") {
            packageName.set("com.jarroyo.feature.launches.sqldelight")
        }
    }
    linkSqlite.set(true)
}

// REVIEW reason of ld: framework 'FirebaseCore' not found
tasks.matching { it.name == "linkDebugTestIosSimulatorArm64" }.configureEach {
    enabled = false
}
tasks.matching { it.name == "linkDebugTestIosX64" }.configureEach {
    enabled = false
}
