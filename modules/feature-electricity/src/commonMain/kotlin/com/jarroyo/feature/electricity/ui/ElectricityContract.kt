@file:OptIn(ExperimentalTime::class)

package com.jarroyo.feature.electricity.ui

import com.jarroyo.library.network.di.ElectricityData
import com.jarroyo.library.ui.shared.ViewEffect
import com.jarroyo.library.ui.shared.ViewEvent
import com.jarroyo.library.ui.shared.ViewState
import com.kizitonwose.calendar.core.now
import kotlinx.datetime.LocalDate
import kotlin.time.ExperimentalTime

object ElectricityContract {
    data class State(
        val dateSelected: LocalDate = LocalDate.now(),
        val electricityData: ElectricityData? = null,
        val loading: Boolean = false,
    ) : ViewState

    sealed class Event : ViewEvent {
        data class OnDateInputChipSelected(val localDate: LocalDate): Event()
        data object OnUpButtonClicked: Event()
        data object OnSwipeToRefresh: Event()
    }

    sealed class Effect : ViewEffect {
        data class ShowSnackbar(val message: String) : Effect()
    }
}
