import com.jarroyo.composeapp.ext.config
import com.jarroyo.composeapp.ext.params
import org.gradle.api.internal.GradleInternal
import org.gradle.internal.logging.LoggingOutputInternal
import java.text.SimpleDateFormat
import java.util.Date

if (config.params.saveBuildLogToFile.get()) {
    val datetime = SimpleDateFormat("yyyy-MM-dd-HH-mm-ss").format(Date())
    val buildLogDir = layout.buildDirectory.dir("logs")
    mkdir(buildLogDir)
    val buildLog = File("${buildLogDir}/buildlog-${datetime}.txt")

    System.setProperty("org.gradle.color.error", "RED")

    val outputListener = StandardOutputListener { output -> buildLog.appendText(output.toString()) }
    (gradle as GradleInternal).services.get(LoggingOutputInternal::class.java).addStandardOutputListener(outputListener)
    (gradle as GradleInternal).services.get(LoggingOutputInternal::class.java).addStandardErrorListener(outputListener)
}
