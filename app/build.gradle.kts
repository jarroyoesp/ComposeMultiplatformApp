import com.android.build.api.dsl.ApplicationExtension

plugins {
    id("composeapp.android-app-conventions")
}

base {
    archivesName.set("composeapp")
}

configure<ApplicationExtension> {
    namespace = config.android.applicationId.get()
    defaultConfig {
        applicationId = config.android.applicationId.get()
        namespace = config.android.applicationId.get()


        testInstrumentationRunner =
            "androidx.test.runner.AndroidJUnitRunner"

        vectorDrawables.useSupportLibrary = true
    }
    
    buildTypes {
        getByName("debug") {
            applicationIdSuffix = ".debug"
        }
        getByName("release") {
            isShrinkResources = true
            isMinifyEnabled = true
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
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
    implementation(libs.androidx.core.ktx)
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
