package com.jarroyo.feature.home

import androidx.compose.runtime.Immutable
import com.jarroyo.library.ui.base.ViewEffect
import com.jarroyo.library.ui.base.ViewEvent
import com.jarroyo.library.ui.base.ViewState

@Immutable
object HomeContract {
    data class State(val text: String = "") : ViewState

    sealed class Event : ViewEvent {
        object OnAcceptButtonClicked : Event()
    }

    sealed class Effect : ViewEffect
}
