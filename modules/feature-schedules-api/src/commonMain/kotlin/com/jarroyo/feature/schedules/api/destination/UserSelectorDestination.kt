package com.jarroyo.feature.schedules.api.destination

import androidx.lifecycle.SavedStateHandle
import androidx.navigation.NamedNavArgument
import androidx.navigation.NavType
import androidx.navigation.navArgument
import com.jarroyo.feature.schedules.api.model.User
import com.jarroyo.library.navigation.api.destination.NavigationDestination
import kotlinx.serialization.Serializable

object UserSelectorDestination: NavigationDestination() {
    private const val USER_LIST_PARAM = "USER_LIST_PARAM"
    override val route: String = "userSelector/{$USER_LIST_PARAM}"

    override val arguments: List<NamedNavArgument> = listOf(
        navArgument(USER_LIST_PARAM) {
            type = NavType.StringType
            nullable = true
            defaultValue = null
        },
    )

    fun get(userList: String): String = route.replace("{$USER_LIST_PARAM}", userList)

    @Serializable
    data class Result(
        override val id: Long = uniqueId,
        val userList: List<User>,
    ) : NavigationDestination.Result()

    object Arguments {
        fun getUserList(savedStateHandle: SavedStateHandle): String? = savedStateHandle[USER_LIST_PARAM]
    }
}
