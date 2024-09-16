import org.jetbrains.compose.desktop.application.dsl.TargetFormat

plugins {
    kotlin("multiplatform")
    id("org.jetbrains.compose")
}
version = "1.0-SNAPSHOT"

kotlin {
    jvm {
        jvmToolchain(11)
        withJava()
    }
    sourceSets {
        jvmMain.dependencies {
            implementation(projects.modules.featureHomeShared)
            implementation(compose.desktop.currentOs)
            implementation(libs.koin.core)
            implementation(libs.tlaster.precompose)
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
