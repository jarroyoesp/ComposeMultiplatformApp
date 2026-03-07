import com.android.build.api.dsl.KotlinMultiplatformAndroidLibraryExtension
import com.jarroyo.composeapp.ext.android
import com.jarroyo.composeapp.ext.config
import org.gradle.accessors.dm.LibrariesForLibs
import org.gradle.kotlin.dsl.configure
import org.gradle.kotlin.dsl.get
import org.gradle.kotlin.dsl.the
import org.jetbrains.kotlin.gradle.dsl.JvmTarget

plugins {
    kotlin("multiplatform")
    id("com.android.kotlin.multiplatform.library")
    id("composeapp.config-conventions")
}

val libs = the<LibrariesForLibs>()

kotlin {
    android {
        namespace = config.android.applicationId.get()
        compileSdk = project.config.android.compileSdk.get()
        minSdk = project.config.android.minSdk.get()

        compilerOptions {
            jvmTarget.set(JvmTarget.fromTarget(project.config.android.javaVersion.get().toString()))
        }
    }

    listOf(
        iosX64(),
        iosArm64(),
        iosSimulatorArm64()
    ).forEach { iosTarget ->
        iosTarget.binaries.framework {
            baseName = "ComposeApp"
            isStatic = true
            linkerOpts("-ObjC")
            export(libs.jetbrains.lifecycle.viewmodel)
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
            implementation(project.dependencies.platform(libs.firebase.android.bom))
            implementation(libs.jetbrains.lifecycle.viewmodel)
            implementation(libs.ktor.client.android)
            implementation(libs.junit)
        }

        commonMain.dependencies {
            api(libs.jetbrains.lifecycle.viewmodel)
            implementation(libs.calendar)
            implementation(libs.coil.compose)
            implementation(libs.coroutines.core)
            implementation(libs.gitlive.firebase.common)
            implementation(libs.gitlive.firebase.firestore)
            implementation(libs.jetbrains.lifecycle.runtime.compose)
            implementation(libs.jetbrains.navigation.compose)
            implementation(libs.koin.annotations)
            implementation(libs.koin.compose)
            implementation(libs.koin.compose.viewmodel)
            implementation(libs.koin.core)
            implementation(libs.kotlin.result)
            implementation(libs.multiplatform.log)

            implementation(libs.coil.compose.core)
            implementation(libs.coil.compose)
            implementation(libs.coil.network)
        }

        commonTest.dependencies {
            api(libs.apollo.testing.support)
            api(libs.coroutines.test)
            api(libs.jetbrains.kotlin.test)
        }

        val desktopMain by getting {
            dependencies {
                implementation(libs.coroutines.swing)
                implementation(libs.ktor.client.java)
                implementation(libs.junit)
            }
        }

        iosMain.dependencies {
            implementation(libs.ktor.client.ios)
        }
    }
}
