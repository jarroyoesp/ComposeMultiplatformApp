plugins {
    id("com.android.library")
    id("composeapp.android-conventions")
}

android {
    namespace = "com.jarroyo.library.test"
    resourcePrefix = "test_"

    defaultConfig {
        consumerProguardFiles("$projectDir/proguard-test-consumer-rules.pro")
    }
}

dependencies {

    api(libs.androidx.lifecycle.viewmodel)
    api(libs.androidx.test.core)
    api(libs.androidx.test.runner)
    api(libs.apollo.testing.support)
    api(libs.coroutines.test)
    api(libs.hilt.android.testing)
    api(libs.junit)
    api(libs.kotlin.test)
    api(libs.kotlin.test.junit)
    api(libs.mockk)
    kapt(libs.hilt.android.compiler)
}
