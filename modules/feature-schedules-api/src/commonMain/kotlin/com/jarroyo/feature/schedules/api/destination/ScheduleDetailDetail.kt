package com.jarroyo.feature.schedules.api.destination

import androidx.lifecycle.SavedStateHandle
import androidx.navigation.NamedNavArgument
import androidx.navigation.NavType
import androidx.navigation.navArgument
import com.jarroyo.library.navigation.api.destination.NavigationDestination
import kotlinx.serialization.Serializable

object ScheduleDetailDestination: NavigationDestination() {
    private const val ID_PARAM = "id"
    override val route: String = "scheduleDetail/{$ID_PARAM}"

    override val arguments: List<NamedNavArgument> = listOf(
        navArgument(ID_PARAM) {
            type = NavType.StringType
            nullable = true
            defaultValue = null
        },
    )

    fun get(id: String): String = route.replace("{$ID_PARAM}", id)

    @Serializable
    data class Result(
        override val id: Long = uniqueId,
        val operationType: OperationType,
    ) : NavigationDestination.Result() {
        enum class OperationType {
            CREATE,
            REMOVE,
            UPDATE,
        }
    }

    object Arguments {
        fun getId(savedStateHandle: SavedStateHandle): String? = savedStateHandle[ID_PARAM]
    }
}
