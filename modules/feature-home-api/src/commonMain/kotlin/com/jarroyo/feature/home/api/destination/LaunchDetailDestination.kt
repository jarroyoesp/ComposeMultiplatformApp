package com.jarroyo.feature.home.api.destination

import com.jarroyo.library.navigation.api.destination.NavigationDestination

class LaunchDetailDestination : NavigationDestination() {
    override val route: String = "launchDetail/{$ID_PARAM}"

    fun get(id: String): String = route.replace("{$ID_PARAM}", id)

    companion object {
        const val ID_PARAM = "id"
    }
}
