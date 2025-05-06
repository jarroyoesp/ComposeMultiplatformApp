package com.jarroyo.feature.electricity.ui

import com.jarroyo.library.network.di.ElectricityData
import com.jarroyo.library.ui.shared.ViewEffect
import com.jarroyo.library.ui.shared.ViewEvent
import com.jarroyo.library.ui.shared.ViewState

object ElectricityContract {
    data class State(
        val electricityData: ElectricityData? = null,
        val loading: Boolean = false,
    ) : ViewState

    sealed class Event : ViewEvent {
        data object OnUpButtonClicked: Event()
        data object OnSwipeToRefresh: Event()
    }

    sealed class Effect : ViewEffect {
        data class ShowSnackbar(val message: String) : Effect()
    }
}
