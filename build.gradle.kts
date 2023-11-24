// Top-level build file where you can add configuration options common to all sub-projects/modules.
plugins {
    id("composeapp.config-conventions")
    id("composeapp.buildlog-conventions")
    id("composeapp.spotless-conventions")
    id("composeapp.versions-conventions")
    //JAE id 'composeapp.dependency-graph-conventions'
    id("composeapp.violation-comments-to-github-conventions")
    id("com.android.application") version "8.0.1" apply false
    id("org.jetbrains.kotlin.android") version "1.8.20" apply false
    id("org.jetbrains.compose") version "1.5.0"
    //JAE alias(libs.plugins.gradledoctor)
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
