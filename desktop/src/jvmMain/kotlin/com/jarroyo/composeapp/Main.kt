package com.jarroyo.composeapp

import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import com.jarroyo.feature.home.shared.ui.RootView
import com.jarroyo.feature.home.shared.di.initKoin

// Migration to PreCompose 1.6.2 import moe.tlaster.precompose.ProvidePreComposeLocals

fun main() {
    initKoin()
    application {
        Window(
            title = "Compose APP",
            onCloseRequest = ::exitApplication,
        ) {
            // Migration to PreCompose 1.6.2 ProvidePreComposeLocals {
                RootView()
            // Migration to PreCompose 1.6.2 }
        }
    }
}
