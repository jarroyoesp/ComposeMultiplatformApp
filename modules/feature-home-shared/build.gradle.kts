plugins {
    id("composeapp.multiplatform-feature-conventions")
    id("org.jetbrains.compose")
    id("org.jetbrains.kotlin.plugin.compose")
}

android {
    namespace = "com.jarroyo.feature.home.shared"
    sourceSets["main"].apply {
        res.srcDirs("src/androidMain/res", "src/commonMain/resources")
    }
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
        }
        commonMain.dependencies {
            implementation(compose.ui)
            implementation(compose.foundation)
            implementation(compose.material)
            implementation(compose.runtime)
            implementation(libs.kotlin.datetime)

            implementation(projects.modules.featureCommon)
            implementation(projects.modules.featureElectricity)
            implementation(projects.modules.featureHomeApi)
            implementation(projects.modules.featureLaunches)
            implementation(projects.modules.featureLaunchesApi)
            implementation(projects.modules.featureSchedules)
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