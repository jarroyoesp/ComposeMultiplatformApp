plugins {
    kotlin("multiplatform")
    id("com.android.library")
}

android {
    namespace = "com.jarroyo.library.test"
    resourcePrefix = "test_"
    compileSdk = libs.versions.androidCompileSdk.get().toInt()
    defaultConfig {
        consumerProguardFiles("$projectDir/proguard-test-consumer-rules.pro")
    }
}

kotlin {
    androidTarget()
    jvm("desktop")
    sourceSets {
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
