package com.jarroyo.feature.electricity.ui

import androidx.lifecycle.viewModelScope
import com.jarroyo.feature.electricity.api.interactor.GetElectricityDataInteractor
import com.jarroyo.feature.electricity.ui.ElectricityContract.Effect
import com.jarroyo.feature.electricity.ui.ElectricityContract.Event
import com.jarroyo.feature.electricity.ui.ElectricityContract.State
import com.jarroyo.library.navigation.api.navigator.AppNavigator
import com.jarroyo.library.ui.shared.BaseViewModel
import kotlinx.coroutines.launch
import kotlinx.datetime.LocalDate

class ElectricityViewModel(
    private val appNavigator: AppNavigator,
    private val getElectricityDataInteractor: GetElectricityDataInteractor,
) : BaseViewModel<Event, State, Effect>() {
    init {
        refreshData(viewState.value.dateSelected)
    }
    override fun provideInitialState() = State()

    override fun handleEvent(event: Event) {
        when(event) {
            is Event.OnDateInputChipSelected -> handleOnDateInputChipSelected(event.localDate)
            is Event.OnSwipeToRefresh -> refreshData(viewState.value.dateSelected)
            is Event.OnUpButtonClicked -> appNavigator.navigateUp()
        }
    }

    private fun handleOnDateInputChipSelected(localDate: LocalDate) {
        updateState { copy(dateSelected = localDate) }
        refreshData(localDate)
    }

    private fun refreshData(localDate: LocalDate) {
        viewModelScope.launch {
            updateState { copy(loading = true) }
            val result = getElectricityDataInteractor(startDate = localDate, endDate = localDate)
            if (result.isOk) {
                updateState { copy(electricityData = result.value) }
            } else {
                sendEffect { Effect.ShowSnackbar(result.error.message.orEmpty()) }
            }
            updateState { copy(loading = false) }
        }
    }
}
