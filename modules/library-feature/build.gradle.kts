import com.android.build.api.dsl.KotlinMultiplatformAndroidLibraryTarget
import org.jetbrains.kotlin.gradle.dsl.KotlinMultiplatformExtension

plugins {
    id("composeapp.multiplatform-library-conventions")
}

kotlin {
    android {
        namespace = "com.jarroyo.library.feature"
        // resourcePrefix = "feature_" // Not supported in KotlinMultiplatformAndroidLibraryTarget
        // defaultConfig { // Not supported in KotlinMultiplatformAndroidLibraryTarget
        //     consumerProguardFiles("$projectDir/proguard-feature-consumer-rules.pro")
        // }
    }

    sourceSets {
        androidMain.dependencies {
        }
        commonMain.dependencies {
            api(projects.modules.libraryNavigationApi)
        }
    }
}
