pluginManagement {
    repositories {
        gradlePluginPortal()
        google()
        mavenCentral()
        maven("https://maven.pkg.jetbrains.space/public/p/compose/dev")
    }
}
// https://docs.gradle.org/7.0/userguide/declaring_dependencies.html#sec:type-safe-project-accessors
enableFeaturePreview("TYPESAFE_PROJECT_ACCESSORS")

@Suppress("UnstableApiUsage")
dependencyResolutionManagement {
    repositories {
        gradlePluginPortal()
        google()
        mavenCentral()
        maven("https://jogamp.org/deployment/maven")
    }
}
rootProject.name = "ComposeApp"
includeBuild("build-conventions")
include(
    // Apps
    ":app",
    ":desktop",
    ":iosapp",

    // Modules
    ":modules:feature-home-api",
    ":modules:feature-home-shared",
    ":modules:feature-schedules",
    ":modules:feature-schedules-api",
    ":modules:library-feature",
    ":modules:library-navigation",
    ":modules:library-navigation-api",
    ":modules:library-network",
    ":modules:library-network-api",
    ":modules:library-test",
    ":modules:library-ui-shared",
)
include(":feature-schedules-api")
include(":modules:feature-schedules")
include(":modules:library-feature")
