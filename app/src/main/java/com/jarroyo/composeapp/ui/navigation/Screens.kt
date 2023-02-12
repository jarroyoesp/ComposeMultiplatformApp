package com.jarroyo.composeapp.ui.navigation

sealed class Screens(val route: String) {
    object HomeScreen : Screens("home_screen")
}
