package com.jarroyo.feature.schedules.ui.list

import androidx.compose.runtime.Immutable
import com.jarroyo.feature.schedules.api.destination.ScheduleDetailDestination
import com.jarroyo.feature.schedules.api.model.Schedule
import com.jarroyo.library.ui.shared.ViewEffect
import com.jarroyo.library.ui.shared.ViewEvent
import com.jarroyo.library.ui.shared.ViewState

@Immutable
object ScheduleListContract {
    data class State(
        val loading: Boolean = false,
        val scheduleList: List<Schedule>? = null,
    ) : ViewState

    sealed class Event : ViewEvent {
        data class OnItemClicked(val id: String) : Event()
        data class OnScheduleUpdated(val operationType: ScheduleDetailDestination.Result.OperationType): Event()
        data object OnAddScheduleButtonClicked: Event()
        data object OnSwipeToRefresh: Event()
    }

    sealed class Effect : ViewEffect {
        data class ShowSnackbar(val message: String) : Effect()
    }
}
