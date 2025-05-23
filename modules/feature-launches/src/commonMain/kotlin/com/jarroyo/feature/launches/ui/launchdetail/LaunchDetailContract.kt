package com.jarroyo.feature.launches.ui.launchdetail

import androidx.compose.runtime.Immutable
import com.jarroyo.composeapp.library.network.api.graphql.fragment.LaunchFragment
import com.jarroyo.feature.launches.api.destination.LaunchDestination
import com.jarroyo.library.ui.shared.ViewEffect
import com.jarroyo.library.ui.shared.ViewEvent
import com.jarroyo.library.ui.shared.ViewState

@Immutable
object LaunchDetailContract {
    data class State(
        val favorite: Boolean? = null,
        val launch: LaunchFragment? = null,
        val loading: Boolean = false,
    ) : ViewState

    sealed class Event : ViewEvent {
        data class OnOpenUrl(val url: String): Event()
        data class OnViewAttached(val id: String): Event()
        data object OnAddFavoritesButtonClicked: Event()
        data object OnUpButtonClicked: Event()
    }

    sealed class Effect : ViewEffect {
        data class SetResultAndNavigate(
            val result: LaunchDestination.Result,
            val navigate: () -> Unit,
        ) : Effect()
        data class ShowSnackbar(val message: String) : Effect()
    }
}
