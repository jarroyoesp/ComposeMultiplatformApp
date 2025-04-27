plugins {
    id("composeapp.multiplatform-feature-conventions")
    id("org.jetbrains.compose")
    id("org.jetbrains.kotlin.plugin.compose")
    alias(libs.plugins.sqldelight)
}

android {
    namespace = "com.jarroyo.feature.home.shared"
    sourceSets["main"].apply {
        res.srcDirs("src/androidMain/res", "src/commonMain/resources")
    }
}

sqldelight {
    databases {
        create("Database") {
            packageName.set("com.jarroyo.feature.home.shared.sqldelight")
        }
    }
    linkSqlite.set(true)
}

kotlin {
    targets.filterIsInstance<org.jetbrains.kotlin.gradle.plugin.mpp.KotlinNativeTarget>()
        .forEach {
            it.binaries.filterIsInstance<org.jetbrains.kotlin.gradle.plugin.mpp.Framework>()
                .forEach { lib ->
                    lib.isStatic = true
                    lib.linkerOpts.add("-lsqlite3")
                }
        }
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
            implementation(libs.kotlin.datetime)
            implementation(libs.sqldelight.coroutines)

            implementation(projects.modules.featureHomeApi)
            implementation(projects.modules.featureSchedules)
            implementation(projects.modules.featureSchedulesApi)
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