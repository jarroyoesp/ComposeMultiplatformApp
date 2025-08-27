@file:OptIn(ExperimentalTime::class)

package com.jarroyo.feature.launches.ui.launchlist

import androidx.lifecycle.viewModelScope
import com.apollographql.apollo.cache.normalized.FetchPolicy
import com.github.michaelbull.result.fold
import com.jarroyo.feature.common.api.interactor.GetInstantFlowInteractor
import com.jarroyo.feature.launches.api.destination.LaunchDestination
import com.jarroyo.feature.launches.api.interactor.GetFavoritesInteractor
import com.jarroyo.feature.launches.api.interactor.GetLaunchesInteractor
import com.jarroyo.feature.launches.ui.launchlist.LaunchListContract.Effect
import com.jarroyo.feature.launches.ui.launchlist.LaunchListContract.Event
import com.jarroyo.feature.launches.ui.launchlist.LaunchListContract.State
import com.jarroyo.library.navigation.api.navigator.AppNavigator
import com.jarroyo.library.ui.shared.BaseViewModel
import kotlinx.coroutines.launch
import org.koin.android.annotation.KoinViewModel
import kotlin.time.ExperimentalTime

@KoinViewModel
class LaunchListViewModel(
    private val appNavigator: AppNavigator,
    private val getFavoritesInteractor: GetFavoritesInteractor,
    private val getLaunchesInteractor: GetLaunchesInteractor,
    private val getInstantFlowInteractor: GetInstantFlowInteractor,
) : BaseViewModel<Event, State, Effect>() {
    init {
        refreshData()
    }

    override fun provideInitialState() = State()

    override fun handleEvent(event: Event) {
        when (event) {
            is Event.FavoritesUpdated -> refreshData(FetchPolicy.NetworkFirst)
            is Event.OnItemClicked -> appNavigator.navigate(LaunchDestination.get(event.id))
            is Event.OnLaunchUpdated -> handleOnLaunchUpdated(event)
            is Event.OnSwipeToRefresh -> handleOnSwipeToRefresh()
        }
    }

    private fun handleOnLaunchUpdated(event: Event.OnLaunchUpdated) {
        sendEffect { Effect.ShowSnackbar("${event.name} was ${event.type} to favorites") }
        refreshData()
    }

    private fun handleOnSwipeToRefresh() {
        refreshData(FetchPolicy.NetworkFirst)
        sendEffect { Effect.ShowSnackbar("Refreshing data...") }
    }

    private fun refreshData(fetchPolicy: FetchPolicy = FetchPolicy.CacheFirst) {
        viewModelScope.launch {
            updateState { copy(loading = true) }

            val result = getLaunchesInteractor(0, 0, fetchPolicy)
            result.fold(
                success = { updateState { copy(rocketList = it) }},
                failure = { sendEffect { Effect.ShowSnackbar(it.message.orEmpty()) } },
            )
            refreshFavorites()
            updateState { copy(loading = false) }
        }
        refreshCurrentLocalDateTime()
    }

    private suspend fun refreshFavorites() {
        val result = getFavoritesInteractor()
        result.fold(
            success = { updateState { copy(favoritesList = it) }},
            failure = { sendEffect { Effect.ShowSnackbar(it.message.orEmpty()) } },
        )
    }

    private fun refreshCurrentLocalDateTime() {
        viewModelScope.launch {
            getInstantFlowInteractor.invoke().collect {
                updateState { copy(currentLocalDateTime = it)}
            }
        }
    }
}
