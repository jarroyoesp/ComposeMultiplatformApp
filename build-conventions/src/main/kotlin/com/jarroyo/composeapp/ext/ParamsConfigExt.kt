package com.jarroyo.composeapp.ext

import org.gradle.api.plugins.ExtensionAware
import org.gradle.api.provider.Property
import org.gradle.kotlin.dsl.getByType

interface ParamsConfigExt : ExtensionAware {
    val espressoCleanup: Property<Boolean>
    val saveBuildLogToFile: Property<Boolean>
}

internal inline val ConfigExt.params: ParamsConfigExt get() = extensions.getByType()
