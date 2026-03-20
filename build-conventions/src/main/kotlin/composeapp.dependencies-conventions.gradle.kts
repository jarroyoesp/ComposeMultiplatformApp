import java.util.Locale

val outputPath = layout.projectDirectory.dir("versions/dependencies").asFile

val compileDependencyReportTask = tasks.register("generateRuntimeDependenciesReport") {
    description = "Generates a text file containing the Runtime classpath dependencies."
}

configurations.matching { it.name.contains("RuntimeClasspath") }.configureEach {
    val configuration = this
    val configurationTask = tasks.register(
        "generate${name.replaceFirstChar { if (it.isLowerCase()) it.titlecase(Locale.ROOT) else it.toString() }}DependenciesReport",
        DependencyReportTask::class.java,
    ) {
        this.configurations = setOf(configuration)
        outputFile = outputPath.resolve("${configuration.name}Dependencies.txt")
        doFirst { outputPath.mkdirs() }
    }
    compileDependencyReportTask.configure { dependsOn(configurationTask) }
}

tasks.named("check") { dependsOn(compileDependencyReportTask) }
