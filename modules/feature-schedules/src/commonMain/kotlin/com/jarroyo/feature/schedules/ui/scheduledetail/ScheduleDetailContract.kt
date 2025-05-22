package com.jarroyo.feature.schedules.ui.scheduledetail

import androidx.compose.runtime.Immutable
import com.jarroyo.feature.schedules.api.destination.ScheduleDetailDestination
import com.jarroyo.feature.schedules.api.model.User
import com.jarroyo.library.ui.shared.ViewEffect
import com.jarroyo.library.ui.shared.ViewEvent
import com.jarroyo.library.ui.shared.ViewState

@Immutable
object ScheduleDetailContract {
    data class State(
        val editAllowed: Boolean = false,
        val id: String? = null,
        val loading: Boolean = false,
        val saveButtonEnabled: Boolean = false,
        val slots: String? = null,
        val time: String? = null,
        val userList: List<User>? = null,
    ) : ViewState

    sealed class Event : ViewEvent {
        data class OnIdValueChanged(val value: String): Event()
        data class OnTimeValueChanged(val value: String): Event()
        data class OnSlotsValueChanged(val value: String): Event()
        data class OnUserSelectedUpdate(val userList: List<User>): Event()
        data object OnAddScheduleButtonClicked: Event()
        data object OnRemoveButtonClicked: Event()
        data object OnUserItemClicked: Event()
        data object OnUpButtonClicked: Event()
    }

    sealed class Effect : ViewEffect {
        data class SetResultAndNavigate(
            val result: ScheduleDetailDestination.Result,
            val navigate: () -> Unit,
        ) : Effect()
        data class ShowSnackbar(val message: String) : Effect()
    }
}
