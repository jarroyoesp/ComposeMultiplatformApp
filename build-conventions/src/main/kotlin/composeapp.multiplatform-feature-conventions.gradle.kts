import org.gradle.accessors.dm.LibrariesForLibs

plugins {
    id ("composeapp.multiplatform-library-conventions")
}
val libs = the<LibrariesForLibs>()
android {
    buildFeatures {
        compose = true
    }
}
kotlin {
    sourceSets {
        commonMain.dependencies {
            api(project(":modules:library-feature"))

            implementation(project(":modules:library-ui-shared"))
            implementation(libs.jetbrains.compose.material3)
        }
    }
}
