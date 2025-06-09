package com.jarroyo.feature.login.ui.login

import androidx.compose.runtime.Immutable
import com.jarroyo.library.ui.shared.ViewEffect
import com.jarroyo.library.ui.shared.ViewEvent
import com.jarroyo.library.ui.shared.ViewState

@Immutable
object LoginContract {
    data class State(
        val email: String? = null,
        val loading: Boolean = false,
        val loginButtonEnabled: Boolean = false,
        val password: String? = null,
    ) : ViewState

    sealed class Event : ViewEvent {
        data class OnEmailValueChanged(val email: String): Event()
        data class OnPasswordValueChanged(val password: String): Event()
        data object OnLoginButtonClicked: Event()
    }

    sealed class Effect : ViewEffect {
        data class ShowSnackbar(val message: String) : Effect()
    }
}
