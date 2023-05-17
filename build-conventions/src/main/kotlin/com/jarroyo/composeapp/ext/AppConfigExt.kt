package com.jarroyo.composeapp.ext

import org.gradle.api.plugins.ExtensionAware
import org.gradle.api.provider.Property
import org.gradle.kotlin.dsl.getByType

interface AppConfigExt : ExtensionAware {
    val deepLinkSchema: Property<String>
    val googleMapsApiKey: Property<String>
    val helpUrl: Property<String>
    val linkProductInfoUrl: Property<String>
    val privacyPolicyUrl: Property<String>
    val termsAndConditionsUrl: Property<String>
}

internal inline val ConfigExt.app: AppConfigExt get() = extensions.getByType()
