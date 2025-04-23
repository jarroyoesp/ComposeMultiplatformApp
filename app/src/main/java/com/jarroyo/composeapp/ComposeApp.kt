package com.jarroyo.composeapp

import android.app.Application
import android.content.Context
import com.google.firebase.FirebaseApp
import com.jarroyo.feature.home.shared.di.initKoin
import dagger.hilt.android.HiltAndroidApp
import org.koin.dsl.module

@HiltAndroidApp
class ComposeApp : Application() {
    override fun onCreate() {
        super.onCreate()
        FirebaseApp.initializeApp(this)
        initKoin(
            listOf(
                module {
                    single<Context> { this@ComposeApp }
                },
            ),
        )
    }
}
