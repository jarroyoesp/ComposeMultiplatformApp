package com.jarroyo.feature.home.ui.rocketdetail

import androidx.lifecycle.viewModelScope
import com.github.michaelbull.result.Err
import com.github.michaelbull.result.Ok
import com.jarroyo.feature.home.api.interactor.GetRocketsInteractor
import com.jarroyo.feature.home.ui.rocketdetail.RocketDetailContract.Effect
import com.jarroyo.feature.home.ui.rocketdetail.RocketDetailContract.Event
import com.jarroyo.feature.home.ui.rocketdetail.RocketDetailContract.State
import com.jarroyo.library.ui.shared.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RocketDetailViewModel @Inject constructor(
    private val getRocketsInteractor: GetRocketsInteractor,
) : BaseViewModel<Event, State, Effect>() {
    init {
        refreshData()
    }

    override fun provideInitialState() = State()

    override fun handleEvent(event: Event) {
        // Empty
    }

    private fun refreshData() {
        viewModelScope.launch {
            updateState { copy(loading = true) }
            when (val result = getRocketsInteractor()) {
                is Ok -> {}
                is Err -> sendEffect { Effect.ShowSnackbar(result.error.toString()) }
            }
            updateState { copy(loading = false) }
        }
    }
}
