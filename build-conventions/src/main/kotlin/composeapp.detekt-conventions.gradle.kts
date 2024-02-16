import io.gitlab.arturbosch.detekt.extensions.DetektExtension
import org.gradle.accessors.dm.LibrariesForLibs
import org.gradle.kotlin.dsl.the

plugins {
    id ("io.gitlab.arturbosch.detekt")
}
val libs = the<LibrariesForLibs>()

detekt {
    toolVersion = libs.versions.detekt.get()
    source.setFrom(
        DetektExtension.DEFAULT_SRC_DIR_KOTLIN,
        DetektExtension.DEFAULT_TEST_SRC_DIR_KOTLIN,
        "src/androidTest/kotlin",
        "src/androidMain/kotlin",
        "src/commonMain/kotlin",
        "src/jvmMain/kotlin",
        "src/nativeMain/kotlin",
        "src/nativeTest/kotlin",
    )
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
