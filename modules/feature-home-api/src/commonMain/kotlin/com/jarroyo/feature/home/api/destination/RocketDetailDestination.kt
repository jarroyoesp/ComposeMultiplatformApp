package com.jarroyo.feature.home.api.destination

import com.jarroyo.library.navigation.api.destination.NavigationDestination

class RocketDetailDestination : NavigationDestination() {
    override val route: String = "rocketDetail/{id}"

    fun get(id: String): String = route.replace("{id}", id)
}
