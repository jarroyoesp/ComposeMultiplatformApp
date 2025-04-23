package com.jarroyo.feature.home.shared.ui

import androidx.lifecycle.viewModelScope
import com.apollographql.apollo.cache.normalized.FetchPolicy
import com.jarroyo.feature.home.api.interactor.GetLaunchesInteractor
import com.jarroyo.feature.home.api.destination.LaunchDetailDestination
import com.jarroyo.feature.home.api.interactor.GetFavoritesInteractor
import com.jarroyo.feature.home.api.interactor.GetSchedulesInteractor
import com.jarroyo.feature.home.shared.ui.HomeContract.Effect
import com.jarroyo.feature.home.shared.ui.HomeContract.Event
import com.jarroyo.feature.home.shared.ui.HomeContract.State
import com.jarroyo.library.navigation.api.navigator.AppNavigator
import com.jarroyo.library.ui.shared.BaseViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.datetime.Clock
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime
import org.koin.android.annotation.KoinViewModel

@KoinViewModel
class HomeViewModel(
    private val appNavigator: AppNavigator,
    private val getFavoritesInteractor: GetFavoritesInteractor,
    private val getLaunchesInteractor: GetLaunchesInteractor,
    private val getSchedulesInteractor: GetSchedulesInteractor,
) : BaseViewModel<Event, State, Effect>() {
    init {
        refreshData()
    }

    override fun provideInitialState() = State()

    override fun handleEvent(event: Event) {
        when (event) {
            is Event.FavoritesUpdated -> refreshData(FetchPolicy.NetworkFirst)
            is Event.OnItemClicked -> appNavigator.navigate(LaunchDetailDestination.get(event.id))
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
            refreshSchedules()
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
    private suspend fun refreshSchedules() {
        val result = getSchedulesInteractor()
        if (result.isOk) {
            updateState { copy(scheduleList = result.value) }
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
