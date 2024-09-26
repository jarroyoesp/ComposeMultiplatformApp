import com.jarroyo.composeapp.ext.android
import com.jarroyo.composeapp.ext.config
import org.gradle.accessors.dm.LibrariesForLibs
import org.gradle.kotlin.dsl.the

plugins {
    kotlin("multiplatform")
    id("com.android.library")
    id("kotlin-parcelize")
    id ("composeapp.config-conventions")
}

val libs = the<LibrariesForLibs>()

android {
    compileSdk = libs.versions.androidCompileSdk.get().toInt()
    sourceSets["main"].manifest.srcFile("src/androidMain/AndroidManifest.xml")
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }
    defaultConfig{
        minSdk = config.android.minSdk.get()
    }
}

kotlin {
    androidTarget()
    listOf(
        iosX64(),
        iosArm64(),
        iosSimulatorArm64()
    ).forEach { iosTarget ->
        iosTarget.binaries.framework {
            baseName = "ComposeApp"
            export(libs.androidx.lifecycle.viewmodel)
        }
    }
    jvm("desktop")
    targets.withType<org.jetbrains.kotlin.gradle.plugin.mpp.KotlinNativeTarget> {
        binaries.all {
            freeCompilerArgs += "-Xdisable-phases=VerifyBitcode"
        }
    }

    sourceSets {
        androidMain.dependencies {
            implementation(libs.androidx.lifecycle.viewmodel)
            implementation(libs.ktor.client.android)
        }

        commonMain.dependencies {
            api(libs.androidx.lifecycle.viewmodel)
            implementation(libs.androidx.lifecycle.runtime.compose)
            implementation(libs.androidx.navigation.compose)
            implementation(libs.calendar)
            implementation(libs.coroutines.core)
            implementation(libs.kamel)
            implementation(libs.koin.annotations)
            implementation(libs.koin.compose)
            implementation(libs.koin.compose.viewmodel)
            implementation(libs.koin.core)
            implementation(libs.kotlin.result)
            implementation(libs.multiplatform.log)
        }

        commonTest.dependencies {
            api(libs.apollo.testing.support)
            api(libs.coroutines.test)
            api(libs.junit)
            api(libs.kotlin.test)
            // api(libs.kotlin.test.junit)
        }

        val desktopMain by getting {
            dependencies {
                implementation(libs.coroutines.core)
                implementation(libs.ktor.client.cio)
            }
        }

        iosMain.dependencies {
            implementation(libs.ktor.client.ios)
        }
    }
}
