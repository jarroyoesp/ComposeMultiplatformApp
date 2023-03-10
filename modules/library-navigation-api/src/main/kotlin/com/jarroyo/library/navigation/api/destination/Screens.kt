package com.jarroyo.library.navigation.api.destination

sealed class Screens(val route: String) {
    object HomeScreen : Screens("home_screen")
    object RocketDetailScreen : Screens("rocket_detail_screen")
}
