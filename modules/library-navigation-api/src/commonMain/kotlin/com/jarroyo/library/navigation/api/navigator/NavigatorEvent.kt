package com.jarroyo.library.navigation.api.navigator

import moe.tlaster.precompose.navigation.NavOptions

sealed class NavigatorEvent {
    data class Directions(val destination: String, val builder: NavOptions?) : NavigatorEvent()
    data object NavigateBack : NavigatorEvent()
    data object NavigateUp : NavigatorEvent()
}
