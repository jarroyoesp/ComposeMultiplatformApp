package com.jarroyo.feature.home.shared.home

import com.jarroyo.feature.home.shared.home.HomeContract.Effect
import com.jarroyo.feature.home.shared.home.HomeContract.Event
import com.jarroyo.feature.home.shared.home.HomeContract.State
import com.jarroyo.library.ui.shared.BaseViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch


class HomeViewModel(
    // private val getRocketsInteractorImpl: GetRocketsInteractorImpl = FeatureHomeKoinComponent().getRocketsInteractorImpl,
) : BaseViewModel<Event, State, Effect>() {

    init {
        refreshData()
    }

    override fun provideInitialState() = State()

    override fun handleEvent(event: Event) {
        when (event) {
            is Event.OnItemClicked -> { sendEffect { Effect.ShowSnackbar("item") }}
            is Event.OnViewAttached -> {}
            is Event.OnSwipeToRefresh -> { sendEffect { Effect.ShowSnackbar("Refresh") }}
        }
    }

    private fun refreshData() {
        updateState {  copy(loading = true) }
        viewModelScope.launch {
            delay(1000)
            // val result = getRocketsInteractorImpl(0, 0, FetchPolicy.NetworkFirst)
            updateState { copy(rocketList = List(40) { "Rocket "}) }
        }
        updateState { copy(loading = false) }
    }
}