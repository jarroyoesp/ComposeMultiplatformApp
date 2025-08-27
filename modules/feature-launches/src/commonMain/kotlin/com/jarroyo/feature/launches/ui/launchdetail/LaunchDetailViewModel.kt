package com.jarroyo.feature.launches.ui.launchdetail

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import co.touchlab.kermit.Logger
import com.github.michaelbull.result.fold
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
                result.fold(
                    success = {
                        sendEffect {
                            Effect.SetResultAndNavigate(
                                result = LaunchDestination.Result(type = "REMOVE", name = viewState.value.launch?.rocket?.rocket?.rocketFragment?.name.orEmpty()),
                                navigate = { appNavigator.navigateBack() },
                            )
                        }
                    },
                    failure = { sendEffect { Effect.ShowSnackbar(it.message.orEmpty()) } },
                )
            } else {
                val result = addFavoriteInteractor(checkNotNull(viewState.value.launch))
                result.fold(
                    success = { sendEffect {
                        Effect.SetResultAndNavigate(
                            result = LaunchDestination.Result(type = "ADDED", name = viewState.value.launch?.rocket?.rocket?.rocketFragment?.name.orEmpty()),
                            navigate = { appNavigator.navigateBack() },
                        )
                    }},
                    failure = { sendEffect { Effect.ShowSnackbar(it.message.orEmpty()) } },
                )
            }
            refreshFavorites()
        }
    }

    private fun refreshFavorites() {
        viewModelScope.launch {
            val result = getFavoritesInteractor()
            result.fold(
                success = { updateState { copy(favorite = it.contains(rocketId)) }},
                failure = { sendEffect { Effect.ShowSnackbar(it.toString()) } },
            )
        }
    }

    private fun refreshData() {
        viewModelScope.launch {
            updateState { copy(loading = true) }
            val result = getLaunchDetailInteractor(checkNotNull(rocketId))
            result.fold(
                success = { updateState { copy(launch = it) }
                    refreshFavorites()},
                failure = { sendEffect { Effect.ShowSnackbar(it.toString()) } },
            )
            updateState { copy(loading = false) }
        }
    }
}
