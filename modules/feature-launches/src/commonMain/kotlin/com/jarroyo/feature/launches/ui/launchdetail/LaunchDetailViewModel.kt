package com.jarroyo.feature.launches.ui.launchdetail

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import co.touchlab.kermit.Logger
import com.jarroyo.feature.common.api.interactor.OpenUrlInBrowserInteractor
import com.jarroyo.feature.launches.api.destination.LaunchDestination
import com.jarroyo.feature.launches.api.interactor.AddFavoriteInteractor
import com.jarroyo.feature.launches.api.interactor.GetFavoritesInteractor
import com.jarroyo.feature.launches.api.interactor.GetLaunchDetailInteractor
import com.jarroyo.feature.launches.api.interactor.RemoveFavoriteInteractor
import com.jarroyo.feature.launches.ui.launchdetail.LaunchDetailContract.Effect
import com.jarroyo.feature.launches.ui.launchdetail.LaunchDetailContract.Event
import com.jarroyo.feature.launches.ui.launchdetail.LaunchDetailContract.State
import com.jarroyo.library.navigation.api.navigator.AppNavigator
import com.jarroyo.library.ui.shared.BaseViewModel
import kotlinx.coroutines.launch
import org.koin.android.annotation.KoinViewModel

@KoinViewModel
class LaunchDetailViewModel(
    private val addFavoriteInteractor: AddFavoriteInteractor,
    private val appNavigator: AppNavigator,
    private val getFavoritesInteractor: GetFavoritesInteractor,
    private val getLaunchDetailInteractor: GetLaunchDetailInteractor,
    private val openUrlInBrowserInteractor: OpenUrlInBrowserInteractor,
    private val removeFavoriteInteractor: RemoveFavoriteInteractor,
    savedStateHandle: SavedStateHandle,
) : BaseViewModel<Event, State, Effect>() {
    private var rocketId: String? = LaunchDestination.Arguments.getId(savedStateHandle)
    init {
        Logger.d("Init $this")
        refreshData()
    }

    override fun provideInitialState() = State()

    override fun handleEvent(event: Event) {
        when (event) {
            is Event.OnAddFavoritesButtonClicked -> handleOnAddFavoritesButtonClicked()
            is Event.OnOpenUrl -> viewModelScope.launch { openUrlInBrowserInteractor(event.url) }
            is Event.OnUpButtonClicked -> appNavigator.navigateBack()
            is Event.OnViewAttached -> {}
        }
    }

    private fun handleOnAddFavoritesButtonClicked() {
        viewModelScope.launch {
            if (viewState.value.favorite == true) {
                val result = removeFavoriteInteractor(checkNotNull(rocketId))
                if (result.isOk) {
                    sendEffect {
                        Effect.SetResultAndNavigate(
                            result = LaunchDestination.Result(type = "REMOVE", name = viewState.value.launch?.rocket?.rocket?.rocketFragment?.name.orEmpty()),
                            navigate = { appNavigator.navigateBack() },
                        )
                    }
                } else {
                    sendEffect { Effect.ShowSnackbar(result.error.message.orEmpty()) }
                }
            } else {
                val result = addFavoriteInteractor(checkNotNull(viewState.value.launch))
                if (result.isOk) {
                    sendEffect {
                        Effect.SetResultAndNavigate(
                            result = LaunchDestination.Result(type = "ADDED", name = viewState.value.launch?.rocket?.rocket?.rocketFragment?.name.orEmpty()),
                            navigate = { appNavigator.navigateBack() },
                        )
                    }
                } else {
                    sendEffect { Effect.ShowSnackbar(result.error.message.orEmpty()) }
                }
            }
            refreshFavorites()
        }
    }

    private fun refreshFavorites() {
        viewModelScope.launch {
            val result = getFavoritesInteractor()
            if (result.isOk) {
                updateState { copy(favorite = result.value.contains(rocketId)) }
            } else {
                sendEffect { Effect.ShowSnackbar(result.error.toString()) }
            }
        }
    }

    private fun refreshData() {
        viewModelScope.launch {
            updateState { copy(loading = true) }
            val result = getLaunchDetailInteractor(checkNotNull(rocketId))
            if (result.isOk) {
                updateState { copy(launch = result.value) }
                refreshFavorites()
            } else {
                sendEffect { Effect.ShowSnackbar(result.error.toString()) }
            }
            updateState { copy(loading = false) }
        }
    }
}
