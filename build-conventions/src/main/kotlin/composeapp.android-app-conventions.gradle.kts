import com.android.build.gradle.internal.tasks.factory.dependsOn
import com.jarroyo.composeapp.gmd.configureGradleManagedDevices
import com.mikepenz.aboutlibraries.plugin.DuplicateMode
import com.mikepenz.aboutlibraries.plugin.DuplicateRule
import com.project.starter.easylauncher.filter.ChromeLikeFilter
import org.gradle.accessors.dm.LibrariesForLibs
import org.jetbrains.kotlin.gradle.dsl.KotlinAndroidProjectExtension

plugins {
    id("org.jetbrains.kotlin.plugin.compose")
    id("com.android.application")
    id("composeapp.android-conventions")
    id("kotlin-parcelize")
    id("com.mikepenz.aboutlibraries.plugin")
    id("composeapp.merged-manifests-conventions")
    id("composeapp.dependencies-conventions")
    id("com.starter.easylauncher")
    id("composeapp.ruler-conventions")
    id("com.google.gms.google-services")
}

val libs = the<LibrariesForLibs>()

android {
    buildFeatures {
        compose = true
    }
    compileOptions {
        isCoreLibraryDesugaringEnabled = true  // https://developer.android.com/studio/write/java8-support#library-desugaring
    }
    configureGradleManagedDevices(this)
    configure<KotlinAndroidProjectExtension> {
        compilerOptions {
            freeCompilerArgs.set(
                freeCompilerArgs.get() + listOf(
                    "-opt-in=androidx.compose.foundation.ExperimentalFoundationApi",
                    "-opt-in=androidx.compose.material.ExperimentalMaterialApi",
                    "-opt-in=androidx.compose.material3.ExperimentalMaterial3Api",
                    "-opt-in=androidx.compose.ui.ExperimentalComposeUiApi",
                )
            )
        }
    }
}

easylauncher {
    buildTypes {
        create("debug") {
            filters(chromeLike(gravity = ChromeLikeFilter.Gravity.TOP, label = "DEBUG", textSizeRatio = 0.20f, labelPadding = 10))
        }
    }
}

aboutLibraries {
    duplicationMode = DuplicateMode.MERGE
    duplicationRule = DuplicateRule.SIMPLE
}

dependencies {

    coreLibraryDesugaring(libs.desugar)
    debugImplementation(libs.leakcanary)
    implementation(libs.androidx.core.ktx)
    implementation(libs.coroutines.core)
    implementation(libs.jetbrains.kotlinx.collections.immutable)
    implementation(libs.kotlin.result)
    implementation(libs.timber)

    testImplementation(project(":modules:library-test"))
    androidTestUtil(libs.androidx.test.orchestrator)
}

tasks.register<Copy>("installGitHooks") {
    from(file("$rootDir/.githooks")) {
        filePermissions {
            unix("rwxr-xr-x")
        }
    }
    into(file("$rootDir/.git/hooks"))
}

afterEvaluate {
    tasks.named("preBuild").dependsOn("installGitHooks")
}
