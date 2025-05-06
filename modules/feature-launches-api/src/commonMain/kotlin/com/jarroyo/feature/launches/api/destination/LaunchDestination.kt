package com.jarroyo.feature.launches.api.destination

import androidx.lifecycle.SavedStateHandle
import androidx.navigation.NamedNavArgument
import androidx.navigation.NavType
import androidx.navigation.navArgument
import com.jarroyo.library.navigation.api.destination.NavigationDestination

object LaunchDestination : NavigationDestination() {
    private const val ID_PARAM = "id"
    override val route: String = "launch/{$ID_PARAM}"

    override val arguments: List<NamedNavArgument> = listOf(
        navArgument(ID_PARAM) {
            type = NavType.StringType
            nullable = true
            defaultValue = null
        },
    )

    fun get(id: String): String = route.replace("{$ID_PARAM}", id)

    object Arguments {
        fun getId(savedStateHandle: SavedStateHandle): String? = savedStateHandle[ID_PARAM]
    }
}
