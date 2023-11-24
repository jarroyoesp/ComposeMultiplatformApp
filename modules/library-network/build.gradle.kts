plugins {
    id ("composeapp.android-library-conventions")
    id ("com.google.dagger.hilt.android")
    alias(libs.plugins.apollo)
    alias(libs.plugins.kotlinx.serialization)
}

android {
    namespace = "com.jarroyo.library.network"
    resourcePrefix = "network_"
    defaultConfig {
        consumerProguardFiles ("$projectDir/proguard-network-consumer-rules.pro")
    }
    kotlinOptions {
        freeCompilerArgs = freeCompilerArgs + listOf(
            "-opt-in=kotlinx.serialization.ExperimentalSerializationApi"
        )
    }
}

apollo {
    service("spacex") {
        packageNamesFromFilePaths()
    }
}

dependencies {
    api (projects.modules.libraryNetworkApi)
    apolloMetadata (projects.modules.libraryNetworkApi)
    //implementation projects.modules.featureAccountApi
    //implementation projects.modules.featureSearchApi
    //implementation projects.modules.libraryAndroidApi
    //implementation projects.modules.libraryFeature
    //implementation projects.modules.libraryI18n
    //implementation projects.modules.libraryNavigationApi
    //implementation projects.modules.libraryPreferencesApi
    //implementation projects.modules.libraryUiApi

    implementation (libs.apollo.cache.sqlite)
    implementation (libs.hilt.android)
    implementation (libs.kotlinx.serialization)
    implementation (libs.okhttp3.logging.interceptor)
    implementation (libs.retrofit)
    implementation (libs.retrofit.converter.scalars)
    implementation (libs.retrofit.kotlinx.serialization)
    implementation (libs.timber)
    implementation (libs.tink)
    kapt (libs.hilt.compiler)

    kaptTest (libs.hilt.android.compiler)
    testImplementation (libs.apollo.testing.support)
    testImplementation (libs.robolectric)
    kaptAndroidTest (libs.hilt.android.compiler)
}
