import org.gradle.accessors.dm.LibrariesForLibs
import org.gradle.kotlin.dsl.the

plugins {
    id ("io.gitlab.arturbosch.detekt")
}
val libs = the<LibrariesForLibs>()

detekt {
    toolVersion = libs.versions.detekt.get()
    source = files("src/main/kotlin", "src/test/kotlin", "src/androidTest/kotlin")
    parallel = true
    autoCorrect = true
}

dependencies {
    detektPlugins (libs.detekt)
    detektPlugins (libs.detekt.rules.compose)
    detektPlugins (libs.detekt.twitter.compose.rules)
}

afterEvaluate {
    tasks.named("check") {
        dependsOn(tasks.named("detektMain"))
        dependsOn(tasks.named("detektTest"))
    }
}
