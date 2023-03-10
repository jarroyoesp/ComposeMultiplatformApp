package com.jarroyo.library.navigation.api.navigator

import android.content.Intent
import androidx.navigation.NavOptionsBuilder

sealed class NavigatorEvent {
    data class Directions(val destination: String, val builder: NavOptionsBuilder.() -> Unit) : NavigatorEvent()
    data class HandleDeepLink(val intent: Intent) : NavigatorEvent()
    object NavigateBack : NavigatorEvent()
    object NavigateUp : NavigatorEvent()
}
