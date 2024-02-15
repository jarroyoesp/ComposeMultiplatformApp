plugins {
    kotlin("multiplatform")
    id("com.android.library")
}

android {
    namespace = "com.jarroyo.library.test"
    resourcePrefix = "test_"
    compileSdk = 34 // TODO
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

// dependencies {
//
//     api(libs.androidx.lifecycle.viewmodel)
//     api(libs.androidx.test.core)
//     api(libs.androidx.test.runner)
//     api(libs.apollo.testing.support)
//     api(libs.coroutines.test)
//     api(libs.hilt.android.testing)
//     api(libs.junit)
//     api(libs.kotlin.test)
//     api(libs.kotlin.test.junit)
//     api(libs.mockk)
//     kapt(libs.hilt.android.compiler)
// }
