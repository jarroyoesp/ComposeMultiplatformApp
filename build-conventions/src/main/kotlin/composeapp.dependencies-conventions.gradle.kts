import com.android.build.gradle.internal.tasks.factory.dependsOn
import java.util.Locale

afterEvaluate {
    val outputPath = "$projectDir/versions/dependencies"
    mkdir(outputPath)
    val compileDependencyReportTask = tasks.register("generateRuntimeDependenciesReport") {
        description = "Generates a text file containing the Runtime classpath dependencies."
    }
    project.configurations.filter { it.name.contains("RuntimeClasspath") }.forEach { configuration ->
        val configurationTask = tasks.register(
                "generate${configuration.name.replaceFirstChar { if (it.isLowerCase()) it.titlecase(Locale.ROOT) else it.toString() }}DependenciesReport",
                DependencyReportTask::class.java,
        ) {
            configurations = setOf(configuration)
            outputFile = File("$outputPath/${configuration.name}Dependencies.txt")
        }
        compileDependencyReportTask.configure { dependsOn(configurationTask) }
    }
    tasks.named("check").dependsOn(tasks.named("generateRuntimeDependenciesReport"))
}
