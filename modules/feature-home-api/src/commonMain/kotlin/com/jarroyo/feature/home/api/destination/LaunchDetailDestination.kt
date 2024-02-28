package com.jarroyo.feature.home.api.destination

import com.jarroyo.library.navigation.api.destination.NavigationDestination

class LaunchDetailDestination : NavigationDestination() {
    override val route: String = "launchDetail/{id}"

    fun get(id: String): String = route.replace("{id}", id)
}
