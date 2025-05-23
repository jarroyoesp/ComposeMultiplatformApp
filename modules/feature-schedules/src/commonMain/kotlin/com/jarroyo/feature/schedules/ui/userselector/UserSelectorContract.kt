package com.jarroyo.feature.schedules.ui.userselector

import androidx.compose.runtime.Immutable
import com.jarroyo.feature.schedules.api.destination.UserSelectorDestination
import com.jarroyo.feature.schedules.api.model.User
import com.jarroyo.library.ui.shared.ViewEffect
import com.jarroyo.library.ui.shared.ViewEvent
import com.jarroyo.library.ui.shared.ViewState

@Immutable
object UserSelectorContract {
    data class State(
        val loading: Boolean = false,
        val multipleSelection: Boolean = false,
        val saveButtonEnabled: Boolean = false,
        val users: List<User>? = null,
        val usersSelectedMap: Map<String, User> = mapOf(),
    ) : ViewState

    sealed class Event : ViewEvent {
        data class OnItemClicked(val user: User, val selected: Boolean) : Event()
        data object OnSaveButtonClicked : Event()
        data object OnUpButtonClicked : Event()
    }

    sealed class Effect : ViewEffect {
        data class SetResultAndNavigate(val result: UserSelectorDestination.Result, val navigate: () -> Unit) : Effect()
        data class ShowErrorSnackbar(
            val message: String,
        ) : Effect()
    }
}
