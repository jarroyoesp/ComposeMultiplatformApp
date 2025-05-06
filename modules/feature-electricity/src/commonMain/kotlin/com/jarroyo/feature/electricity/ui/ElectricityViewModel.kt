package com.jarroyo.feature.electricity.ui

import androidx.lifecycle.viewModelScope
import com.jarroyo.feature.electricity.api.interactor.GetElectricityDataInteractor
import com.jarroyo.feature.electricity.ui.ElectricityContract.Effect
import com.jarroyo.feature.electricity.ui.ElectricityContract.Event
import com.jarroyo.feature.electricity.ui.ElectricityContract.State
import com.jarroyo.library.navigation.api.navigator.AppNavigator
import com.jarroyo.library.ui.shared.BaseViewModel
import com.kizitonwose.calendar.core.now
import kotlinx.coroutines.launch
import kotlinx.datetime.LocalDate

class ElectricityViewModel(
    private val appNavigator: AppNavigator,
    private val getElectricityDataInteractor: GetElectricityDataInteractor,
) : BaseViewModel<Event, State, Effect>() {
    init {
        refreshData()
    }
    override fun provideInitialState() = State()

    override fun handleEvent(event: Event) {
        when(event) {
            is Event.OnSwipeToRefresh -> refreshData()
            is Event.OnUpButtonClicked -> appNavigator.navigateUp()
        }
    }

    private fun refreshData() {
        viewModelScope.launch {
            updateState { copy(loading = true) }
            val result = getElectricityDataInteractor(startDate = LocalDate.now(), endDate = LocalDate.now())
            if (result.isOk) {
                updateState { copy(electricityData = result.value) }
            } else {
                sendEffect { Effect.ShowSnackbar(result.error.message.orEmpty()) }
            }
            updateState { copy(loading = false) }
        }
    }
}
