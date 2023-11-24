package com.jarroyo.feature.home.ui

import androidx.lifecycle.viewModelScope
import com.github.michaelbull.result.Err
import com.github.michaelbull.result.Ok
import com.jarroyo.feature.home.api.interactor.GetRocketsInteractor
import com.jarroyo.feature.home.ui.HomeContract.Effect
import com.jarroyo.feature.home.ui.HomeContract.Event
import com.jarroyo.feature.home.ui.HomeContract.State
import com.jarroyo.library.navigation.api.destination.Screens
import com.jarroyo.library.navigation.api.navigator.AppNavigator
import com.jarroyo.library.ui.shared.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val getRocketsInteractor: GetRocketsInteractor,
    private val appNavigator: AppNavigator,
) : BaseViewModel<Event, State, Effect>() {
    init {
        refreshData()
    }

    override fun provideInitialState() = State()

    override fun handleEvent(event: Event) {
        when (event) {
            Event.OnAcceptButtonClicked -> appNavigator.navigate(Screens.RocketDetailScreen.route)
            Event.OnSwipeToRefresh -> refreshData()
        }
    }

    private fun refreshData() {
        viewModelScope.launch {
            updateState { copy(loading = true) }
            when (val result = getRocketsInteractor()) {
                is Ok -> updateState { copy(rocketList = result.value) }
                is Err -> sendEffect {
                    Effect.ShowSnackbar(
                        result.error.message ?: result.error.toString(),
                    )
                }
            }
            updateState { copy(loading = false) }
        }
    }
}
