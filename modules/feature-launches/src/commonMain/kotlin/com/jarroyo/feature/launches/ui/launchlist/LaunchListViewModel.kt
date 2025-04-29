package com.jarroyo.feature.launches.ui.launchlist

import androidx.lifecycle.viewModelScope
import com.apollographql.apollo.cache.normalized.FetchPolicy
import com.jarroyo.feature.launches.api.destination.LaunchDestination
import com.jarroyo.feature.launches.api.interactor.GetFavoritesInteractor
import com.jarroyo.feature.launches.api.interactor.GetLaunchesInteractor
import com.jarroyo.feature.launches.ui.launchlist.LaunchListContract.Effect
import com.jarroyo.feature.launches.ui.launchlist.LaunchListContract.Event
import com.jarroyo.feature.launches.ui.launchlist.LaunchListContract.State
import com.jarroyo.library.navigation.api.navigator.AppNavigator
import com.jarroyo.library.ui.shared.BaseViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.datetime.Clock
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime
import org.koin.android.annotation.KoinViewModel

@KoinViewModel
class LaunchListViewModel(
    private val appNavigator: AppNavigator,
    private val getFavoritesInteractor: GetFavoritesInteractor,
    private val getLaunchesInteractor: GetLaunchesInteractor,
) : BaseViewModel<Event, State, Effect>() {
    init {
        refreshData()
    }

    override fun provideInitialState() = State()

    override fun handleEvent(event: Event) {
        when (event) {
            is Event.FavoritesUpdated -> refreshData(FetchPolicy.NetworkFirst)
            is Event.OnItemClicked -> appNavigator.navigate(LaunchDestination.get(event.id))
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
            val result = getLaunchesInteractor(0, 0, fetchPolicy)
            if (result.isOk) {
                updateState { copy(rocketList = result.value) }
            } else {
                sendEffect { Effect.ShowSnackbar(result.error.message.orEmpty()) }
            }
            refreshFavorites()
            updateState { copy(loading = false) }
        }
        refreshCurrentLocalDateTime()
    }

    private suspend fun refreshFavorites() {
        val result = getFavoritesInteractor()
        if (result.isOk) {
            updateState { copy(favoritesList = result.value) }
        } else {
            sendEffect { Effect.ShowSnackbar(result.error.message.orEmpty()) }
        }
    }

    private fun refreshCurrentLocalDateTime() {
        viewModelScope.launch {
            while (true) {
                delay(1000)
                updateState {
                    copy(
                        currentLocalDateTime = Clock.System.now().toLocalDateTime(TimeZone.UTC),
                    )
                }
            }
        }
    }
}
