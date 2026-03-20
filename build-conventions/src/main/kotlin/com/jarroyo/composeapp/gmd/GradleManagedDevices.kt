package com.jarroyo.composeapp.gmd

import com.android.build.api.dsl.CommonExtension
import org.gradle.kotlin.dsl.get
import org.gradle.kotlin.dsl.invoke

/**
 * Configure project for Gradle managed devices
 */
internal fun configureGradleManagedDevices(
    commonExtension: CommonExtension,
) {
    val pixel6Api31 = DeviceConfig("Pixel 6", 31, "aosp")
    val pixel6Api28 = DeviceConfig("Pixel 6", 28, "aosp")

    val myDevices = listOf(pixel6Api31, pixel6Api28)

    commonExtension.testOptions.apply {
        managedDevices {
            localDevices {
                myDevices.forEach { deviceConfig ->
                    create(deviceConfig.taskName).apply {
                        device = deviceConfig.device
                        apiLevel = deviceConfig.apiLevel
                        systemImageSource = deviceConfig.systemImageSource
                    }
                }
            }
        }
    }
}

private data class DeviceConfig(
    val device: String,
    val apiLevel: Int,
    val systemImageSource: String,
) {
    val taskName = buildString {
        append(device.lowercase().replace(" ", ""))
        append("Api")
        append(apiLevel.toString())
        append(systemImageSource.replace("-", ""))
    }
}
