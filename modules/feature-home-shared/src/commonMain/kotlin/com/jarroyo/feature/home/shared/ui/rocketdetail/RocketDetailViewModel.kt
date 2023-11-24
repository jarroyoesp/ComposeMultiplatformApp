package com.jarroyo.feature.home.shared.ui.rocketdetail

import com.jarroyo.feature.home.shared.ui.rocketdetail.RocketDetailContract.Effect
import com.jarroyo.feature.home.shared.ui.rocketdetail.RocketDetailContract.Event
import com.jarroyo.feature.home.shared.ui.rocketdetail.RocketDetailContract.State
import com.jarroyo.library.navigation.api.navigator.AppNavigator
import com.jarroyo.library.ui.shared.BaseViewModel
import kotlinx.coroutines.launch

class RocketDetailViewModel (
    private val appNavigator: AppNavigator,
    // private val getRocketsInteractor: GetRocketsInteractor,
) : BaseViewModel<Event, State, Effect>() {
    init {
        refreshData()
    }

    override fun provideInitialState() = State()

    override fun handleEvent(event: Event) {
        when(event) {
            Event.OnUpButtonClicked -> appNavigator.navigateBack()
        }
    }

    private fun refreshData() {
        viewModelScope.launch {
            updateState { copy(loading = true) }
            // when (val result = getRocketsInteractor()) {
            // is Ok -> {}
            // is Err -> sendEffect { Effect.ShowSnackbar(result.error.toString()) }
            // }
            updateState { copy(loading = false) }
        }
    }
}
