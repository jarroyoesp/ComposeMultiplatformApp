package com.jarroyo.composeapp.ext

import org.gradle.api.JavaVersion
import org.gradle.api.plugins.ExtensionAware
import org.gradle.api.provider.Property
import org.gradle.kotlin.dsl.getByType

interface AndroidConfigExt : ExtensionAware {
    val accountType: Property<String>
    val applicationId: Property<String>
    val compileSdk: Property<Int>
    val javaVersion: Property<JavaVersion>
    val minSdk: Property<Int>
    val targetSdk: Property<Int>
}

internal inline val ConfigExt.android: AndroidConfigExt get() = extensions.getByType()
