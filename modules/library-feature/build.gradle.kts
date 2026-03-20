import com.android.build.api.dsl.KotlinMultiplatformAndroidLibraryTarget
import org.jetbrains.kotlin.gradle.dsl.KotlinMultiplatformExtension

plugins {
    id("composeapp.multiplatform-library-conventions")
}

kotlin {
    android {
        namespace = "com.jarroyo.library.feature"
    }

    sourceSets {
        androidMain.dependencies {
        }
        commonMain.dependencies {
            api(projects.modules.libraryNavigationApi)
        }
    }
}
