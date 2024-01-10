package com.jarroyo.feature.home.shared.ui.rocketdetail

import co.touchlab.kermit.Logger
import com.github.michaelbull.result.Err
import com.github.michaelbull.result.Ok
import com.jarroyo.feature.home.api.interactor.GetLaunchDetailInteractor
import com.jarroyo.feature.home.shared.ui.rocketdetail.RocketDetailContract.Effect
import com.jarroyo.feature.home.shared.ui.rocketdetail.RocketDetailContract.Event
import com.jarroyo.feature.home.shared.ui.rocketdetail.RocketDetailContract.State
import com.jarroyo.library.navigation.api.navigator.AppNavigator
import com.jarroyo.library.ui.shared.BaseViewModel
import kotlinx.coroutines.launch
import moe.tlaster.precompose.viewmodel.viewModelScope

class RocketDetailViewModel(
    private val appNavigator: AppNavigator,
    private val getLaunchDetailInteractor: GetLaunchDetailInteractor,
) : BaseViewModel<Event, State, Effect>() {
    private var rocketId: String? = null

    override fun provideInitialState() = State()

    override fun handleEvent(event: Event) {
        when (event) {
            is Event.OnUpButtonClicked -> appNavigator.navigateBack()
            is Event.OnViewAttached -> handleOnViewAttached(event.id)
        }
    }

    private fun handleOnViewAttached(id: String) {
        Logger.d("RocketDetailViewModel - OnViewAttached id: $id")
        rocketId = id
        refreshData()
    }

    private fun refreshData() {
        viewModelScope.launch {
            updateState { copy(loading = true) }
            when (val result = getLaunchDetailInteractor(checkNotNull(rocketId))) {
                is Ok -> updateState { copy(launch = result.value) }
                is Err -> sendEffect { Effect.ShowSnackbar(result.error.toString()) }
            }
            updateState { copy(loading = false) }
        }
    }
}
