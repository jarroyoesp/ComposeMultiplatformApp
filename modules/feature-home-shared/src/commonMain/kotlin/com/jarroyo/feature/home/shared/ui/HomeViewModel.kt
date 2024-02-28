package com.jarroyo.feature.home.shared.ui

import com.apollographql.apollo3.cache.normalized.FetchPolicy
import com.github.michaelbull.result.Err
import com.github.michaelbull.result.Ok
import com.jarroyo.feature.home.api.interactor.GetLaunchesInteractor
import com.jarroyo.feature.home.api.destination.LaunchDetailDestination
import com.jarroyo.feature.home.api.interactor.GetFavoritesInteractor
import com.jarroyo.feature.home.shared.ui.HomeContract.Effect
import com.jarroyo.feature.home.shared.ui.HomeContract.Event
import com.jarroyo.feature.home.shared.ui.HomeContract.State
import com.jarroyo.library.navigation.api.navigator.AppNavigator
import com.jarroyo.library.ui.shared.BaseViewModel
import kotlinx.coroutines.launch
import moe.tlaster.precompose.viewmodel.viewModelScope

class HomeViewModel(
    private val appNavigator: AppNavigator,
    private val getFavoritesInteractor: GetFavoritesInteractor,
    private val getLaunchesInteractor: GetLaunchesInteractor,
) : BaseViewModel<Event, State, Effect>() {
    override fun provideInitialState() = State()

    override fun handleEvent(event: Event) {
        when (event) {
            is Event.FavoritesUpdated -> refreshData(FetchPolicy.NetworkFirst)
            is Event.OnItemClicked -> appNavigator.navigate(LaunchDetailDestination().get(event.id))
            is Event.OnViewAttached -> refreshData(FetchPolicy.NetworkFirst)
            is Event.OnSwipeToRefresh -> handleOnSwipeToRefresh()
        }
    }

    private fun handleOnSwipeToRefresh() {
        refreshData(FetchPolicy.NetworkFirst)
        sendEffect { Effect.ShowSnackbar("Refreshing data...") }
    }

    private fun refreshData(fetchPolicy: FetchPolicy = FetchPolicy.CacheFirst) {
        viewModelScope.launch {
            updateState { copy(loading = true) }
            when (val result = getLaunchesInteractor(0, 0, fetchPolicy)) {
                is Ok -> updateState { copy(rocketList = result.value) }
                is Err -> sendEffect { Effect.ShowSnackbar(result.error.message.orEmpty()) }
            }
            refreshFavorites()
            updateState { copy(loading = false) }
        }
    }

    private suspend fun refreshFavorites() {
        when (val result = getFavoritesInteractor()) {
            is Ok -> updateState { copy(favoritesList = result.value) }
            is Err -> sendEffect { Effect.ShowSnackbar(result.error.message.orEmpty()) }
        }
    }
}
