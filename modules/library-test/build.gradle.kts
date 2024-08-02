plugins {
    kotlin("multiplatform")
    id("com.android.library")
    id("composeapp.config-conventions")
}

android {
    namespace = "com.jarroyo.library.test"
    resourcePrefix = "test_"
    compileSdk = libs.versions.androidCompileSdk.get().toInt()
    defaultConfig {
        minSdk = config.android.minSdk.get()
        consumerProguardFiles("$projectDir/proguard-test-consumer-rules.pro")
    }
    packaging {
        resources {
            // Use this block to exclude conflicting files that breaks your APK assemble task
            excludes.add("META-INF/LICENSE-notice.md")
            excludes.add("META-INF/LICENSE.md")
            excludes.add("META-INF/NOTICE.md")
        }
    }
    // Configuración para usar Java 17
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }
}

kotlin {
    androidTarget()
    jvm("desktop")
    sourceSets {
        androidMain.dependencies {
            api(libs.androidx.test.core)
            api(libs.androidx.test.runner)
        }
        commonMain.dependencies {
            api(libs.apollo.testing.support)
            api(libs.coroutines.test)
            api(libs.hilt.android.testing)
            api(libs.junit)
            api(libs.kotlin.test)
            api(libs.kotlin.test.junit)
            api(libs.mockk)
        }

        commonTest.dependencies {
            api(libs.apollo.testing.support)
            api(libs.coroutines.test)
            api(libs.hilt.android.testing)
            api(libs.junit)
            api(libs.kotlin.test)
            api(libs.kotlin.test.junit)
            api(libs.mockk)
        }
    }
}
