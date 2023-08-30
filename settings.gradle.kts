pluginManagement {
    repositories {
        gradlePluginPortal()
        google()
        mavenCentral()
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
    }
}
rootProject.name = "ComposeApp"
includeBuild("build-conventions")
include(
    // Apps
    ":app",

    // Modules
    ":modules:feature-home",
    ":modules:feature-home-api",
    ":modules:library-navigation",
    ":modules:library-navigation-api",
    ":modules:library-network",
    ":modules:library-network-api",
    ":modules:library-test",
    ":modules:library-ui",
    ":modules:library-ui-shared",
)
include(":modules:library-ui-shared")
