import com.jarroyo.composeapp.ext.android
import com.jarroyo.composeapp.ext.config
import org.gradle.accessors.dm.LibrariesForLibs
import org.gradle.kotlin.dsl.the
import org.jetbrains.kotlin.gradle.dsl.JvmTarget
import org.jetbrains.kotlin.gradle.dsl.KotlinAndroidProjectExtension

plugins {
    id ("composeapp.android-library-conventions")
    id ("com.google.dagger.hilt.android")
}

val libs = the<LibrariesForLibs>()

android {
    buildFeatures {
        compose = true
    }
    configure<KotlinAndroidProjectExtension> {
        compilerOptions {
            freeCompilerArgs.set(
                freeCompilerArgs.get() + listOf(
                    "-opt-in=androidx.compose.foundation.ExperimentalFoundationApi",
                    "-opt-in=androidx.compose.foundation.layout.ExperimentalLayoutApi",
                    "-opt-in=androidx.compose.material.ExperimentalMaterialApi",
                    "-opt-in=androidx.compose.material3.ExperimentalMaterial3Api",
                    "-opt-in=androidx.compose.ui.ExperimentalComposeUiApi"
                )
            )
        }
    }
}

dependencies {
    //api projects.modules.libraryFeature
    //implementation projects.modules.libraryAndroidApi
    //implementation projects.modules.libraryI18n
    //implementation projects.modules.libraryLoggingApi
    implementation(project(":modules:library-ui"))

    implementation (libs.androidx.activity.compose)
    implementation (libs.androidx.core.ktx)
    implementation (libs.androidx.lifecycle.runtime.compose)
    implementation (libs.androidx.lifecycle.viewmodel)
    implementation (libs.androidx.navigation.fragment)
    implementation (libs.androidx.navigation.ui.ktx)
    implementation (libs.hilt.android)
    implementation (libs.hilt.navigation.compose)
    implementation (libs.kotlin.result)
    implementation (libs.kotlin.result.coroutines)
    implementation (libs.timber)
    kapt (libs.hilt.compiler)

    androidTestImplementation (libs.androidx.test.espresso.core)
    androidTestImplementation (libs.androidx.test.ext.junit)
    androidTestImplementation (libs.androidx.test.runner)
    androidTestUtil (libs.androidx.test.orchestrator)
    testImplementation(project(":modules:library-test"))
    androidTestImplementation(project(":modules:library-test"))
}
