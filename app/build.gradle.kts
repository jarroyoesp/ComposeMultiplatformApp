plugins {
    id ("composeapp.android-app-conventions")
    alias(libs.plugins.tripletplay)
}

android {
    defaultConfig {
        applicationId = config.android.applicationId.get()
        setProperty("archivesBaseName", "composeapp")

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"  // https://github.com/google/dagger/issues/2033

        vectorDrawables.useSupportLibrary = true
    }
    buildTypes {
        getByName("debug") {
            namespace = config.android.applicationId.get() + ".debug"
            applicationIdSuffix = ".debug"
        }
        getByName("release") {
            namespace = config.android.applicationId.get()
            isShrinkResources = true
            isMinifyEnabled = true
            proguardFiles(getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro")
        }
    }
}

dependencies {
    // Modules
    implementation(projects.modules.featureHomeShared)

    implementation(libs.androidx.activity.compose)
    implementation(libs.gitlive.firebase.firestore)
    implementation(libs.jetbrains.compose.ui)
    implementation(libs.jetbrains.compose.material3)
    implementation(libs.koin.android)
    testImplementation(libs.junit)
    androidTestImplementation(libs.junit)
    androidTestImplementation(libs.androidx.test.espresso.core)
    androidTestImplementation(libs.androidx.compose.ui.test.junit4)
    androidTestImplementation(libs.gitlive.firebase.firestore)
    debugImplementation(libs.androidx.compose.tooling)
    debugImplementation(libs.androidx.compose.ui.test.manifest)

    detektPlugins(libs.detekt)
    detektPlugins(libs.detekt.rules.compose)
    detektPlugins(libs.detekt.twitter.compose.rules)

    implementation(libs.androidx.compose.tooling)
}
