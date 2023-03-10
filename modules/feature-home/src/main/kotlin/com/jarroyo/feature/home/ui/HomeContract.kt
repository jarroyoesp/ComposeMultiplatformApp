package com.jarroyo.feature.home.ui

import androidx.compose.runtime.Immutable
import com.jarroyo.composeapp.library.network.api.graphql.RocketsQuery
import com.jarroyo.library.ui.base.ViewEffect
import com.jarroyo.library.ui.base.ViewEvent
import com.jarroyo.library.ui.base.ViewState

@Immutable
object HomeContract {
    data class State(
        val loading: Boolean = false,
        val rocketList: List<RocketsQuery.Rocket>? = null,
    ) : ViewState

    sealed class Event : ViewEvent {
        object OnAcceptButtonClicked : Event()
    }

    sealed class Effect : ViewEffect {
        data class ShowSnackbar(val message: String) : Effect()
    }
}
