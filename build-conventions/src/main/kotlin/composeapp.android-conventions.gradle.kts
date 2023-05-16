import com.android.build.api.dsl.CommonExtension
import com.android.build.gradle.BaseExtension
import com.android.build.gradle.api.AndroidBasePlugin
import com.jarroyo.composeapp.ext.android
import com.jarroyo.composeapp.ext.app
import com.jarroyo.composeapp.ext.config
import org.jetbrains.kotlin.gradle.dsl.KotlinJvmOptions
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile
import org.gradle.accessors.dm.LibrariesForLibs

plugins {
    id ("kotlin-android")
    id ("kotlin-kapt")
    id ("composeapp.detekt-conventions")
    id ("composeapp.config-conventions")
}

val libs = the<LibrariesForLibs>()

kapt {
    useBuildCache = true
    correctErrorTypes = true
    javacOptions {
        option("-Xmaxerrs", Integer.MAX_VALUE)
    }
}

plugins.withType<AndroidBasePlugin>().configureEach {
    extensions.configure<BaseExtension> {
        compileSdkVersion(config.android.compileSdk.get())

        defaultConfig {
            minSdk = config.android.minSdk.get()
            targetSdk = config.android.targetSdk.get()

            testInstrumentationRunner = "com.jarroyo.library.test.runner.HiltTestRunner"
            // The following argument makes the Android Test Orchestrator run its
            // "pm clear" command after each test invocation. This command ensures
            // that the app's state is completely cleared between tests.
            setTestInstrumentationRunnerArguments(mutableMapOf("clearPackageData" to "true"))
        }
        compileOptions {
            sourceCompatibility = config.android.javaVersion.get()
            targetCompatibility = config.android.javaVersion.get()
        }
        testOptions {
            execution = "ANDROIDX_TEST_ORCHESTRATOR"
            animationsDisabled = true
            unitTests {
                isIncludeAndroidResources = true
                isReturnDefaultValues = true
            }
        }
        if (this is CommonExtension<*, *, *, *>) {
            lint {
                abortOnError = true
                checkAllWarnings = false
                checkDependencies = true
                checkReleaseBuilds = false
                ignoreTestSources = true
                warningsAsErrors = false
                disable.add("ResourceType")
                lintConfig = file("${project.rootDir}/config/lint/lint.xml")
            }

            kotlinOptions {
                freeCompilerArgs = freeCompilerArgs + listOf(
                    "-opt-in=kotlin.RequiresOptIn",
                    "-opt-in=kotlinx.coroutines.ExperimentalCoroutinesApi",
                    "-opt-in=kotlinx.coroutines.FlowPreview",
                )
                jvmTarget = config.android.javaVersion.get().toString()
            }
        }
        packagingOptions {
            resources {
                // Use this block to exclude conflicting files that breaks your APK assemble task
                excludes.add("META-INF/LICENSE.md")
                excludes.add("META-INF/LICENSE-notice.md")
            }
        }
    }
}

fun CommonExtension<*, *, *, *>.kotlinOptions(block: KotlinJvmOptions.() -> Unit) {
    (this as ExtensionAware).extensions.configure("kotlinOptions", block)
}

kotlin {
    sourceSets.all {
        languageSettings.progressiveMode = true // deprecations and bug fixes for unstable code take effect immediately
    }
}
tasks {
    withType<KotlinCompile> {
        kotlinOptions {
            jvmTarget = config.android.javaVersion.get().toString()
        }
    }

    withType<Test> {
        testLogging.events("skipped", "failed")
    }
}
