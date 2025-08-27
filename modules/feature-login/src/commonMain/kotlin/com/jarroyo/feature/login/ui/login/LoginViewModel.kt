package com.jarroyo.feature.login.ui.login

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import com.github.michaelbull.result.fold
import com.jarroyo.feature.login.api.interactor.LoginInteractor
import com.jarroyo.feature.login.ui.login.LoginContract.Effect
import com.jarroyo.feature.login.ui.login.LoginContract.Event
import com.jarroyo.feature.login.ui.login.LoginContract.State
import com.jarroyo.library.navigation.api.navigator.AppNavigator
import com.jarroyo.library.ui.shared.BaseViewModel
import kotlinx.coroutines.launch
import org.koin.android.annotation.KoinViewModel

@KoinViewModel
class LoginViewModel(
    private val appNavigator: AppNavigator,
    private val loginInteractor: LoginInteractor,
    savedStateHandle: SavedStateHandle,
) : BaseViewModel<Event, State, Effect>() {
    override fun provideInitialState() = State()

    override fun handleEvent(event: Event) {
        when (event) {
            is Event.OnEmailValueChanged -> handleOnEmailValueChanged(event.email)
            is Event.OnLoginButtonClicked -> handleOnLoginButtonClicked()
            is Event.OnPasswordValueChanged -> handleOnPasswordValueChanged(event.password)
        }
    }

    private fun handleOnEmailValueChanged(email: String) {
        updateState { copy(email = email) }
        updateLoginButtonVisibility()
    }

    private fun handleOnLoginButtonClicked() {
        viewModelScope.launch {
            val result = loginInteractor(email = checkNotNull(viewState.value.email), password = checkNotNull(viewState.value.password))
            result.fold(
                success = { sendEffect { Effect.ShowSnackbar("LOGIN SUCCESS") }
                    appNavigator.navigateHome()
                           },
                failure = { sendEffect { Effect.ShowSnackbar("LOGIN ERROR: $it") } },
            )
        }
    }

    private fun handleOnPasswordValueChanged(password: String) {
        updateState { copy(password = password) }
        updateLoginButtonVisibility()
    }
    private fun updateLoginButtonVisibility() {
        updateState { copy(loginButtonEnabled = viewState.value.email?.isNotEmpty() == true && viewState.value.password?.isNotEmpty() == true) }
    }
}
