plugins {
    id("composeapp.multiplatform-library-conventions")
    id("org.jetbrains.compose")
    id("org.jetbrains.kotlin.plugin.compose")
}

kotlin {
    android {
        namespace = "com.jarroyo.library.ui.shared"
    }

    sourceSets {
        commonMain {
            resources.srcDirs("src/commonMain/resources")
        }

        commonMain.dependencies {
            api(libs.jetbrains.compose.material.icons.extended)
            api(libs.jetbrains.lifecycle.viewmodel)
            api(libs.jetbrains.lifecycle.viewmodel.compose)
            api(libs.jetbrains.lifecycle.runtime.compose)
            api(libs.jetbrains.compose.components.resources)
            api(libs.jetbrains.compose.foundation)
            api(libs.jetbrains.compose.material)
            api(libs.jetbrains.compose.material3)
            api(libs.jetbrains.compose.ui)
            api(libs.jetbrains.compose.ui.graphics)
            api(libs.compose.placeholder)
            api(libs.coroutines.core)
            api(projects.modules.libraryFeature)
            
            implementation(libs.jetbrains.kotlinx.serialization)
            implementation(projects.modules.libraryNavigationApi)
        }
    }
}
