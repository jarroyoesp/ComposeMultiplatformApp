package com.jarroyo.feature.home.shared.home

import com.jarroyo.composeapp.library.network.api.graphql.RocketsQuery
import com.jarroyo.library.ui.shared.ViewEffect
import com.jarroyo.library.ui.shared.ViewEvent
import com.jarroyo.library.ui.shared.ViewState


object HomeContract {
    data class State(
        val loading: Boolean = false,
        val rocketList: List<RocketsQuery.Rocket>? = null,
        val text: String = "Hola que hace",
    ) : ViewState

    sealed class Event : ViewEvent {
        data class OnItemClicked(val id: String) : Event()
        data object OnSwipeToRefresh: Event()
        data object OnViewAttached: Event()
    }

    sealed class Effect : ViewEffect {
        data class ShowSnackbar(val message: String) : Effect()
    }
}
