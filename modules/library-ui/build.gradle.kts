plugins {
    id ("composeapp.android-library-conventions")
    id ("com.google.dagger.hilt.android")
}

android {
    namespace = "com.jarroyo.library.ui"
    resourcePrefix = "ui_"
    compileSdk = libs.versions.androidCompileSdk.get().toInt()
    defaultConfig {
        minSdk = config.android.minSdk.get()
        testOptions.targetSdk = config.android.targetSdk.get()
        consumerProguardFiles ("$projectDir/proguard-ui-consumer-rules.pro")
    }
    buildFeatures {
        compose = true
    }
    composeOptions {
        kotlinCompilerExtensionVersion = libs.versions.androidx.compose.compiler.get()
    }
    kotlinOptions {
        freeCompilerArgs = freeCompilerArgs + listOf(
            "-opt-in=androidx.compose.foundation.ExperimentalFoundationApi",
            "-opt-in=androidx.compose.material.ExperimentalMaterialApi",
            "-opt-in=androidx.compose.material3.ExperimentalMaterial3Api",
            "-opt-in=androidx.compose.ui.ExperimentalComposeUiApi",
        )
    }
}

dependencies {
    implementation (projects.modules.libraryNavigationApi)
    api (libs.accompanist.flowlayout)
    api (libs.accompanist.navigation.material)
    api (libs.accompanist.pager)
    api (libs.accompanist.pager.indicators)
    api (libs.accompanist.placeholder)
    api (libs.accompanist.systemuicontroller)
    api (libs.androidx.appcompat)
    api (libs.androidx.compose.material) // Still needed for stuff missing in M3, like ModalBottomSheetLayout
    api (libs.androidx.compose.material3)
    api (libs.androidx.compose.runtime)
    api (libs.androidx.compose.runtime.livedata)
    api (libs.androidx.compose.tooling)
    api (libs.androidx.compose.ui)
    api (libs.androidx.core.splashscreen)
    api (libs.androidx.navigation.compose)
    api (libs.androidx.paging)
    api (libs.coil.compose)
    api (libs.material)
    api (libs.speeddial)
    implementation (libs.hilt.android)
    debugApi (libs.androidx.customview)  // Workaround for https://issuetracker.google.com/issues/227767363
    debugApi (libs.androidx.customview.poolingcontainer)  // Workaround for https://issuetracker.google.com/issues/227767363
    kapt (libs.hilt.compiler)
}