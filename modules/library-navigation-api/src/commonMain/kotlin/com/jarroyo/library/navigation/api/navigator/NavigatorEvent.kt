package com.jarroyo.library.navigation.api.navigator

import androidx.navigation.NavOptionsBuilder

sealed class NavigatorEvent {
    data class Directions(val destination: String, val builder: NavOptionsBuilder.() -> Unit) : NavigatorEvent()
    data object NavigateBack : NavigatorEvent()
    data object NavigateUp : NavigatorEvent()
}
