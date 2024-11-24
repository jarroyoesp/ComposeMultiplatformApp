import com.jarroyo.composeapp.ext.AndroidConfigExt
import com.jarroyo.composeapp.ext.ConfigExt
import com.jarroyo.composeapp.ext.ParamsConfigExt
import com.jarroyo.composeapp.ext.getOrNull

val config = extensions.create<ConfigExt>("config").apply {
    extensions.create<AndroidConfigExt>("android").apply {
        accountType.convention("com.jarroyo.composeapp.auth")
        applicationId.convention("com.jarroyo.composeapp")
        compileSdk.convention(35)
        javaVersion.convention(JavaVersion.VERSION_17)
        minSdk.convention(24)
        targetSdk.convention(35)
    }

    extensions.create<ParamsConfigExt>("params").apply {
        espressoCleanup.convention((rootProject.extra.getOrNull("espressoCleanup") as String?).toBoolean())
        saveBuildLogToFile.convention((rootProject.extra.getOrNull("saveBuildLogToFile") as String?).toBoolean())
    }
}
