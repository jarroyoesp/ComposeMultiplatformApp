package com.jarroyo.composeapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.jarroyo.composeapp.ui.theme.ComposeAppTheme
import com.jarroyo.feature.home.shared.di.initKoin
import com.jarroyo.feature.home.shared.ui.RootView
import dagger.hilt.android.AndroidEntryPoint
import org.koin.android.ext.koin.androidContext

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        initKoin {
            androidContext(applicationContext)
        }
        setContent {
            ComposeAppTheme {
                RootView()
            }
        }
    }
}
