import org.jetbrains.kotlin.gradle.dsl.JvmTarget
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    kotlin("multiplatform")
    id("composeapp.multiplatform-library-conventions")
    id("composeapp.config-conventions")
}

kotlin {
    android {
        namespace = "com.jarroyo.library.test"
        compileSdk = libs.versions.androidCompileSdk.get().toInt()

        packaging {
            resources {
                excludes.add("META-INF/LICENSE-notice.md")
                excludes.add("META-INF/LICENSE.md")
                excludes.add("META-INF/NOTICE.md")
            }
        }
    }

    sourceSets {
        androidMain.dependencies {
            api(libs.androidx.test.core)
            api(libs.androidx.test.runner)
            api(libs.junit)
            api(libs.mockk.android)
        }
        commonMain.dependencies {
            api(libs.apollo.testing.support)
            api(libs.coroutines.test)
        }

        commonTest.dependencies {
            api(libs.apollo.testing.support)
            api(libs.coroutines.test)
            api(libs.jetbrains.kotlin.test)
        }

        val desktopMain by getting {
            dependencies {
                api(libs.junit)
                api(libs.mockk)
            }
        }
    }
}
