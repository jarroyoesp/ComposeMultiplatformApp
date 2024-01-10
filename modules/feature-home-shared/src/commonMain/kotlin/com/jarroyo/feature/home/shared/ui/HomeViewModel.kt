package com.jarroyo.feature.home.shared.ui

import com.apollographql.apollo3.cache.normalized.FetchPolicy
import com.github.michaelbull.result.Err
import com.github.michaelbull.result.Ok
import com.jarroyo.feature.home.api.interactor.GetRocketsInteractor
import com.jarroyo.feature.home.shared.destination.RocketDetailDestination
import com.jarroyo.feature.home.shared.ui.HomeContract.Effect
import com.jarroyo.feature.home.shared.ui.HomeContract.Event
import com.jarroyo.feature.home.shared.ui.HomeContract.State
import com.jarroyo.library.navigation.api.navigator.AppNavigator
import com.jarroyo.library.ui.shared.BaseViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import moe.tlaster.precompose.viewmodel.viewModelScope

class HomeViewModel(
    private val appNavigator: AppNavigator,
    private val getRocketsInteractor: GetRocketsInteractor,
) : BaseViewModel<Event, State, Effect>() {
    init {
        refreshData()
    }

    override fun provideInitialState() = State()

    override fun handleEvent(event: Event) {
        when (event) {
            is Event.OnItemClicked -> appNavigator.navigate(RocketDetailDestination().get(event.id))
            is Event.OnViewAttached -> {}
            is Event.OnSwipeToRefresh -> sendEffect { Effect.ShowSnackbar("Refresh") }
        }
    }

    private fun refreshData() {
        viewModelScope.launch {
            updateState { copy(loading = true) }
            delay(1000)
            when (val result = getRocketsInteractor(0, 0, FetchPolicy.NetworkFirst)) {
                is Err -> sendEffect { Effect.ShowSnackbar(result.error.message.orEmpty()) }
                is Ok -> updateState { copy(rocketList = result.value) }
            }
            updateState { copy(loading = false) }
        }
    }
}