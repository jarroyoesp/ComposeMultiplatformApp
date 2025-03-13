package com.jarroyo.feature.home.shared.ui

import com.jarroyo.composeapp.library.network.api.graphql.fragment.LaunchFragment
import com.jarroyo.feature.home.api.interactor.Schedule
import com.jarroyo.library.ui.shared.ViewEffect
import com.jarroyo.library.ui.shared.ViewEvent
import com.jarroyo.library.ui.shared.ViewState
import kotlinx.datetime.LocalDateTime

object HomeContract {
    data class State(
        val currentLocalDateTime: LocalDateTime? = null,
        val favoritesList: List<String>? = null,
        val scheduleList: List<String>? = null,
        val loading: Boolean = false,
        val rocketList: List<LaunchFragment>? = null,
    ) : ViewState

    sealed class Event : ViewEvent {
        data class OnItemClicked(val id: String) : Event()
        data object FavoritesUpdated: Event()
        data object OnSwipeToRefresh: Event()
    }

    sealed class Effect : ViewEffect {
        data class ShowSnackbar(val message: String) : Effect()
    }
}
