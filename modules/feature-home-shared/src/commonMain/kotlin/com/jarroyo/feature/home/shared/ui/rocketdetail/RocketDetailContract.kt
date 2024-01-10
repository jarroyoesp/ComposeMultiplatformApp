package com.jarroyo.feature.home.shared.ui.rocketdetail

import androidx.compose.runtime.Immutable
import com.jarroyo.composeapp.library.network.api.graphql.LaunchDetailQuery
import com.jarroyo.library.ui.shared.ViewEffect
import com.jarroyo.library.ui.shared.ViewEvent
import com.jarroyo.library.ui.shared.ViewState

@Immutable
object RocketDetailContract {
    data class State(
        val launch: LaunchDetailQuery.Launch? = null,
        val loading: Boolean = false,
    ) : ViewState

    sealed class Event : ViewEvent {
        data class OnViewAttached(val id: String): Event()
        data object OnUpButtonClicked: Event()
    }

    sealed class Effect : ViewEffect {
        data class ShowSnackbar(val message: String) : Effect()
    }
}
