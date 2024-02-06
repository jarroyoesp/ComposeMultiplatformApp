package com.jarroyo.feature.home.shared.ui

import com.jarroyo.composeapp.library.network.api.graphql.LaunchesQuery
import com.jarroyo.library.ui.shared.ViewEffect
import com.jarroyo.library.ui.shared.ViewEvent
import com.jarroyo.library.ui.shared.ViewState

object HomeContract {
    data class State(
        val loading: Boolean = false,
        val rocketList: List<LaunchesQuery.Launch>? = null,
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
