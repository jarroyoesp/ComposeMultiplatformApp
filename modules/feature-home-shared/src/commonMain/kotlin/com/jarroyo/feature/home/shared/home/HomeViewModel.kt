package com.jarroyo.feature.home.shared.home

import com.apollographql.apollo3.cache.normalized.FetchPolicy
import com.github.michaelbull.result.Err
import com.github.michaelbull.result.Ok
import com.jarroyo.feature.home.api.interactor.GetRocketsInteractor
import com.jarroyo.feature.home.shared.di.FeatureHomeKoinComponent
import com.jarroyo.feature.home.shared.home.HomeContract.Effect
import com.jarroyo.feature.home.shared.home.HomeContract.Event
import com.jarroyo.feature.home.shared.home.HomeContract.State
import com.jarroyo.feature.home.shared.interactor.GetRocketsInteractorImpl
import com.jarroyo.library.ui.shared.BaseViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch


class HomeViewModel(
    private val getRocketsInteractor: GetRocketsInteractor = FeatureHomeKoinComponent().getRocketsInteractor,
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
            when (val result = getRocketsInteractor(0, 0, FetchPolicy.NetworkFirst)) {
                is Err -> {}
                is Ok -> updateState { copy(rocketList = result.value) }
            }

        }
        updateState { copy(loading = false) }
    }
}