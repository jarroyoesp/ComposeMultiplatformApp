// Top-level build file where you can add configuration options common to all sub-projects/modules.
plugins {
    id("composeapp.config-conventions")
    id("composeapp.buildlog-conventions")
    id("composeapp.spotless-conventions")
    id("composeapp.versions-conventions")
    id("composeapp.violation-comments-to-github-conventions")
    alias(libs.plugins.multiplatform)
}

subprojects {
    gradle.projectsEvaluated {
        tasks.withType<JavaCompile> {
            options.compilerArgs.add("-Xlint:unchecked")
            options.compilerArgs.add("-Xlint:deprecation")
        }
    }
}

tasks.withType<Wrapper> {
    description = "Regenerates the Gradle Wrapper files"
    distributionType = Wrapper.DistributionType.ALL
    gradleVersion = libs.versions.gradle.get()
}
