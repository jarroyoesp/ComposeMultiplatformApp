import org.jetbrains.compose.desktop.application.dsl.TargetFormat

plugins {
    kotlin("multiplatform")
    id("org.jetbrains.compose")
    id("org.jetbrains.kotlin.plugin.compose")
}
version = "1.0-SNAPSHOT"

kotlin {
    jvm {
        //jvmToolchain(11)
        withJava()
    }
    sourceSets {
        jvmMain.dependencies {
            implementation(projects.modules.featureHomeShared)
            implementation(compose.desktop.currentOs)
            implementation(libs.gitlive.firebase.java)
            implementation(libs.koin.core)
        }
    }
}

compose.desktop {
    application {
        mainClass = "com.jarroyo.composeapp.MainKt"
        nativeDistributions {
            targetFormats(TargetFormat.Dmg, TargetFormat.Msi, TargetFormat.Deb)
            packageName = "desktop"
            packageVersion = "1.0.0"
        }
    }
}
