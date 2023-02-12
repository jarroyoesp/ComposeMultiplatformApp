package com.jarroyo.feature.home

import com.jarroyo.feature.home.HomeContract.Effect
import com.jarroyo.feature.home.HomeContract.Event
import com.jarroyo.feature.home.HomeContract.State
import com.jarroyo.library.ui.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    // private val linkNavigator: LinkNavigator,
) : BaseViewModel<Event, State, Effect>() {
    override fun provideInitialState() = State()

    override fun handleEvent(event: Event) {
        when (event) {
            Event.OnAcceptButtonClicked -> {}
        }
    }
}
