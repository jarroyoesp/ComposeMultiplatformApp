package com.jarroyo.feature.common.interactor

import java.awt.Desktop
import java.net.URI

actual fun openUrl(url: String) {
    val uri = url.let { URI.create(it) } ?: return
    Desktop.getDesktop().browse(uri)
}