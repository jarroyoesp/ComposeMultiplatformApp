package com.jarroyo.feature.schedules.ui.list

import com.jarroyo.feature.schedules.api.interactor.Schedule
import com.jarroyo.library.ui.shared.ViewEffect
import com.jarroyo.library.ui.shared.ViewEvent
import com.jarroyo.library.ui.shared.ViewState

object ScheduleListContract {
    data class State(
        val loading: Boolean = false,
        val scheduleList: List<Schedule>? = null,
    ) : ViewState

    sealed class Event : ViewEvent {
        data class OnItemClicked(val id: String) : Event()
        data object OnSwipeToRefresh: Event()
        data object OnUpButtonClicked: Event()
    }

    sealed class Effect : ViewEffect {
        data class ShowSnackbar(val message: String) : Effect()
    }
}
