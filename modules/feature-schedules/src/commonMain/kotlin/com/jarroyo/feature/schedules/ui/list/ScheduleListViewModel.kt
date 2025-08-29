package com.jarroyo.feature.schedules.ui.list

import androidx.lifecycle.viewModelScope
import com.github.michaelbull.result.fold
import com.jarroyo.feature.account.api.interactor.GetAccountInteractor
import com.jarroyo.feature.schedules.api.destination.ScheduleDetailDestination
import com.jarroyo.feature.schedules.api.destination.ScheduleDetailDestination.Result
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
    private val getAccountInteractor: GetAccountInteractor,
    private val getSchedulesInteractor: GetSchedulesInteractor,
) : BaseViewModel<Event, State, Effect>() {
    init {
        viewModelScope.launch {
            if (getAccountInteractor().isOk) {
                refreshData()
            } else {
                appNavigator.navigateToLogin {
                    launchSingleTop = true
                    popUpTo(0) { inclusive = true }
                }
            }
        }
    }

    override fun provideInitialState() = State()

    override fun handleEvent(event: Event) {
        when (event) {
            is Event.OnAddScheduleButtonClicked -> appNavigator.navigate(ScheduleDetailDestination.get(""))
            is Event.OnItemClicked -> appNavigator.navigate(ScheduleDetailDestination.get(event.id))
            is Event.OnScheduleUpdated -> handleOnScheduleUpdated(event.operationType)
            is Event.OnSwipeToRefresh -> handleOnSwipeToRefresh()
        }
    }

    private fun handleOnScheduleUpdated(operationType: Result.OperationType) {
        refreshData()
        val message = when(operationType) {
            Result.OperationType.CREATE -> "Created"
            Result.OperationType.REMOVE -> "Removed"
            Result.OperationType.UPDATE -> "Updated"
        }
        sendEffect { Effect.ShowSnackbar(message) }
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
        result.fold(
            success = { updateState { copy(scheduleList = it) }},
            failure = { sendEffect { Effect.ShowSnackbar(it.message.orEmpty()) }},
        )
    }
}
