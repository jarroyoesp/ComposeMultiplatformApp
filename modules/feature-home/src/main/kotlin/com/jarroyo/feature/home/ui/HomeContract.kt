package com.jarroyo.feature.home.ui

import androidx.compose.runtime.Immutable
import com.jarroyo.composeapp.library.network.api.graphql.RocketsQuery
import com.jarroyo.library.ui.shared.ViewEffect
import com.jarroyo.library.ui.shared.ViewEvent
import com.jarroyo.library.ui.shared.ViewState

@Immutable
object HomeContract {
    data class State(
        val loading: Boolean = false,
        val rocketList: List<RocketsQuery.Rocket>? = null,
    ) : ViewState

    sealed class Event : ViewEvent {
        object OnAcceptButtonClicked : Event()
        object OnSwipeToRefresh : Event()
    }

    sealed class Effect : ViewEffect {
        data class ShowSnackbar(val message: String) : Effect()
    }
}
