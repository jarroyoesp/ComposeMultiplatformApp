package com.jarroyo.composeapp.ext

import org.gradle.api.plugins.ExtraPropertiesExtension

@Suppress("UNCHECKED_CAST")
fun <T> ExtraPropertiesExtension.getOrNull(name: String): T? = if (has(name)) get(name) as? T else null
