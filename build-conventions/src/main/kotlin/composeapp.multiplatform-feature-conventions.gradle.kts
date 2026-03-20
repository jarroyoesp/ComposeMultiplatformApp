import com.android.build.api.dsl.LibraryExtension
import org.gradle.accessors.dm.LibrariesForLibs
import org.gradle.kotlin.dsl.configure
import org.gradle.kotlin.dsl.the

plugins {
    id ("composeapp.multiplatform-library-conventions")
}
val libs = the<LibrariesForLibs>()

plugins.withId("com.android.library") {
    extensions.configure<LibraryExtension> {
        buildFeatures {
            compose = true
        }
    }
}

kotlin {
    sourceSets {
        commonMain.dependencies {
            api(project.project(":modules:library-ui-shared"))
        }
    }
}
