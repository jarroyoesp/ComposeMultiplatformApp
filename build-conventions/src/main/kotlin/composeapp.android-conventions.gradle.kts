import com.android.build.api.dsl.ApplicationExtension
import com.android.build.api.dsl.LibraryExtension
import com.jarroyo.composeapp.ext.ConfigExt
import com.jarroyo.composeapp.ext.AndroidConfigExt
import org.gradle.accessors.dm.LibrariesForLibs
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile
import org.jetbrains.kotlin.gradle.dsl.JvmTarget
import org.gradle.api.JavaVersion

plugins {
    id("composeapp.detekt-conventions")
    id("composeapp.config-conventions")
}

val libs = the<LibrariesForLibs>()

/**
 * Explicitly get the custom android configuration to avoid conflicts with the AGP 'android' extension
 */
fun Project.getAndroidConfig(): AndroidConfigExt {
    return extensions.getByType<ConfigExt>().extensions.getByType<AndroidConfigExt>()
}

plugins.withId("com.android.application") {
    extensions.configure<ApplicationExtension> {
        val config = project.getAndroidConfig()
        compileSdk = config.compileSdk.get()

        defaultConfig {
            minSdk = config.minSdk.get()
            targetSdk = config.targetSdk.get()
            testInstrumentationRunnerArguments["clearPackageData"] = "true"
        }

        compileOptions {
            sourceCompatibility = config.javaVersion.get()
            targetCompatibility = config.javaVersion.get()
        }

        testOptions {
            animationsDisabled = true
            unitTests {
                isIncludeAndroidResources = true
                isReturnDefaultValues = true
            }
        }

        lint {
            abortOnError = true
            checkAllWarnings = false
            checkDependencies = true
            checkReleaseBuilds = false
            ignoreTestSources = true
            warningsAsErrors = false
            disable.add("ResourceType")
            lintConfig = project.file("${project.rootDir}/config/lint/lint.xml")
        }

        packaging {
            resources {
                excludes.add("META-INF/LICENSE-notice.md")
                excludes.add("META-INF/LICENSE.md")
                excludes.add("META-INF/NOTICE.md")
            }
        }
    }
}

plugins.withId("com.android.library") {
    extensions.configure<LibraryExtension> {
        val config = project.getAndroidConfig()
        compileSdk = config.compileSdk.get()

        defaultConfig {
            minSdk = config.minSdk.get()
            testInstrumentationRunnerArguments["clearPackageData"] = "true"
        }

        compileOptions {
            sourceCompatibility = config.javaVersion.get()
            targetCompatibility = config.javaVersion.get()
        }

        testOptions {
            animationsDisabled = true
            unitTests {
                isIncludeAndroidResources = true
                isReturnDefaultValues = true
            }
        }

        lint {
            abortOnError = true
            checkAllWarnings = false
            checkDependencies = true
            checkReleaseBuilds = false
            ignoreTestSources = true
            warningsAsErrors = false
            disable.add("ResourceType")
            lintConfig = project.file("${project.rootDir}/config/lint/lint.xml")
        }

        packaging {
            resources {
                excludes.add("META-INF/LICENSE-notice.md")
                excludes.add("META-INF/LICENSE.md")
                excludes.add("META-INF/NOTICE.md")
            }
        }
    }
}

tasks.withType<KotlinCompile>().configureEach {
    compilerOptions {
        val config = project.getAndroidConfig()
        jvmTarget.set(JvmTarget.fromTarget(config.javaVersion.get().toString()))
    }
}

tasks.withType<Test>().configureEach {
    testLogging.events("skipped", "failed")
}
