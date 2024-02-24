package com.jarroyo.feature.home.shared.ui.rocketdetail

import co.touchlab.kermit.Logger
import com.github.michaelbull.result.Err
import com.github.michaelbull.result.Ok
import com.jarroyo.feature.home.api.interactor.AddFavoriteInteractor
import com.jarroyo.feature.home.api.interactor.GetFavoritesInteractor
import com.jarroyo.feature.home.api.interactor.GetLaunchDetailInteractor
import com.jarroyo.feature.home.api.interactor.RemoveFavoriteInteractor
import com.jarroyo.feature.home.shared.ui.rocketdetail.RocketDetailContract.Effect
import com.jarroyo.feature.home.shared.ui.rocketdetail.RocketDetailContract.Event
import com.jarroyo.feature.home.shared.ui.rocketdetail.RocketDetailContract.State
import com.jarroyo.library.navigation.api.navigator.AppNavigator
import com.jarroyo.library.ui.shared.BaseViewModel
import kotlinx.coroutines.launch
import moe.tlaster.precompose.viewmodel.viewModelScope

class RocketDetailViewModel(
    private val addFavoriteInteractor: AddFavoriteInteractor,
    private val appNavigator: AppNavigator,
    private val getFavoritesInteractor: GetFavoritesInteractor,
    private val getLaunchDetailInteractor: GetLaunchDetailInteractor,
    private val removeFavoriteInteractor: RemoveFavoriteInteractor,
) : BaseViewModel<Event, State, Effect>() {
    private var rocketId: String? = null

    init {
        Logger.d("Init ${this}")
    }

    override fun provideInitialState() = State()

    override fun handleEvent(event: Event) {
        when (event) {
            is Event.OnAddFavoritesButtonClicked -> handleOnAddFavoritesButtonClicked()
            is Event.OnUpButtonClicked -> appNavigator.navigateBack()
            is Event.OnViewAttached -> handleOnViewAttached(event.id)
        }
    }

    private fun handleOnAddFavoritesButtonClicked() {
        viewModelScope.launch {
            if (viewState.value.favorite) {
                when (val result = removeFavoriteInteractor(checkNotNull(rocketId))) {
                    is Ok -> sendEffect { Effect.ShowSnackbar("Rocket removed from Favorites") }
                    is Err -> sendEffect { Effect.ShowSnackbar(result.error.message.orEmpty()) }
                }
            } else {
                when (val result = addFavoriteInteractor(checkNotNull(viewState.value.launch))) {
                    is Ok -> sendEffect { Effect.ShowSnackbar("Rocket added to Favorites") }
                    is Err -> sendEffect { Effect.ShowSnackbar(result.error.message.orEmpty()) }
                }
            }
            refreshFavorites()
        }
    }

    private fun handleOnViewAttached(id: String) {
        Logger.d("RocketDetailViewModel - OnViewAttached id: $id")
        rocketId = id
        refreshFavorites()
        refreshData()
    }

    private fun refreshFavorites() {
        viewModelScope.launch {
            when (val result = getFavoritesInteractor()) {
                is Ok -> updateState { copy(favorite = result.value.contains(rocketId)) }
                is Err -> sendEffect { Effect.ShowSnackbar(result.error.toString()) }
            }
        }
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
