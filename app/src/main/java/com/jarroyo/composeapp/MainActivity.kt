package com.jarroyo.composeapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.jarroyo.composeapp.ui.theme.ComposeAppTheme
import com.jarroyo.feature.home.shared.ui.RootView
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ComposeAppTheme {
                RootView()
            }
        }
    }
}
