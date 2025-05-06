package com.jarroyo.feature.schedules.ui.list

import androidx.lifecycle.viewModelScope
import com.jarroyo.feature.schedules.api.interactor.GetSchedulesInteractor
import com.jarroyo.feature.schedules.ui.list.ScheduleListContract.Effect
import com.jarroyo.feature.schedules.ui.list.ScheduleListContract.Event
import com.jarroyo.feature.schedules.ui.list.ScheduleListContract.State
import com.jarroyo.library.navigation.api.navigator.AppNavigator
import com.jarroyo.library.ui.shared.BaseViewModel
import kotlinx.coroutines.launch
import org.koin.android.annotation.KoinViewModel

@KoinViewModel
class ScheduleListViewModel(
    private val appNavigator: AppNavigator,
    private val getSchedulesInteractor: GetSchedulesInteractor,
) : BaseViewModel<Event, State, Effect>() {
    init {
        refreshData()
    }

    override fun provideInitialState() = State()

    override fun handleEvent(event: Event) {
        when (event) {
            is Event.OnItemClicked -> {}
            is Event.OnSwipeToRefresh -> handleOnSwipeToRefresh()
            is Event.OnUpButtonClicked -> appNavigator.navigateBack()
        }
    }

    private fun handleOnSwipeToRefresh() {
        refreshData()
        sendEffect { Effect.ShowSnackbar("Refreshing data...") }
    }

    private fun refreshData() {
        viewModelScope.launch {
            updateState { copy(loading = true) }
            refreshSchedules()
            updateState { copy(loading = false) }
        }
    }
    private suspend fun refreshSchedules() {
        val result = getSchedulesInteractor()
        if (result.isOk) {
            updateState { copy(scheduleList = result.value) }
        } else {
            sendEffect { Effect.ShowSnackbar(result.error.message.orEmpty()) }
        }
    }
}
