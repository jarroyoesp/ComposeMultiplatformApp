package com.jarroyo.composeapp.ext

import org.gradle.api.Project
import org.gradle.api.plugins.ExtensionAware
import org.gradle.kotlin.dsl.getByType

interface ConfigExt : ExtensionAware

internal inline val Project.config: ConfigExt get() = extensions.getByType()
