import gradle.kotlin.dsl.accessors._5a57100ac4c76e3931d64b835baf62f0.test
import gradle.kotlin.dsl.accessors._5a57100ac4c76e3931d64b835baf62f0.testDebug
import org.gradle.accessors.dm.LibrariesForLibs
import org.gradle.kotlin.dsl.the

plugins {
    kotlin("multiplatform")
    id("com.android.library")
    id("kotlin-parcelize")
}

val libs = the<LibrariesForLibs>()

android {
    compileSdk = 34 // TODO
    sourceSets["main"].manifest.srcFile("src/androidMain/AndroidManifest.xml")
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }
}

kotlin {
    androidTarget()
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
            implementation(libs.coroutines.core)
            implementation(libs.kamel)
            implementation(libs.koin.core)
            implementation(libs.kotlin.result)
            implementation(libs.multiplatform.log)
            implementation(libs.tlaster.precompose)
            implementation(libs.tlaster.precompose.viewmodel)

        }

        commonTest.dependencies {
            implementation(project(":modules:library-test"))
        }

        val desktopMain by getting {
            dependencies {
                implementation(libs.coroutines.core)
                implementation(libs.ktor.client.cio)
            }
        }
    }
}