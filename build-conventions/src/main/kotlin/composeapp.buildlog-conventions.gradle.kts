import com.jarroyo.composeapp.ext.config
import com.jarroyo.composeapp.ext.params
import java.text.SimpleDateFormat
import java.util.Date

if (config.params.saveBuildLogToFile.get()) {
    val datetime = SimpleDateFormat("yyyy-MM-dd-HH-mm-ss").format(Date())
    val buildLogDir = layout.buildDirectory.dir("logs")

    System.setProperty("org.gradle.color.error", "RED")

    gradle.taskGraph.whenReady {
        val logDir = buildLogDir.get().asFile
        logDir.mkdirs()
        val buildLog = File(logDir, "buildlog-${datetime}.txt")
        val outputListener = StandardOutputListener { output -> buildLog.appendText(output.toString()) }
        logging.addStandardOutputListener(outputListener)
        logging.addStandardErrorListener(outputListener)
    }
}
