package com.jarroyo.composeapp

import android.app.Application
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import com.jarroyo.feature.home.shared.ui.RootView
import com.jarroyo.feature.home.shared.di.initKoin
import com.google.firebase.Firebase
import com.google.firebase.FirebaseOptions
import com.google.firebase.FirebasePlatform
import com.google.firebase.initialize

// Migration to PreCompose 1.6.2 import moe.tlaster.precompose.ProvidePreComposeLocals

fun main() {
    initializeFirebase()
    initKoin()
    application {
        Window(
            title = "Compose APP",
            onCloseRequest = ::exitApplication,
        ) {
            RootView()
        }
    }
}

fun initializeFirebase() {
    FirebasePlatform.initializeFirebasePlatform(
        object : FirebasePlatform() {
            override fun store(key: String, value: String) {
                // TODO
            }
            override fun retrieve(key: String) = ""
            // TODO
            override fun clear(key: String) {
                // TODO
            }
            override fun log(msg: String) = println(msg)
        }
    )

    val options: FirebaseOptions = FirebaseOptions.Builder()
        .setProjectId("virtualgym-684f7")
        .setApplicationId("1:129903346150:android:bf4d3697156f8e7009d627")
        .build()

    Firebase.initialize(Application(), options)
}
