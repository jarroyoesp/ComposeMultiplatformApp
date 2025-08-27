package com.jarroyo.feature.schedules.ui.userselector

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import com.github.michaelbull.result.fold
import com.jarroyo.feature.schedules.api.destination.UserSelectorDestination
import com.jarroyo.feature.schedules.api.interactor.GetUserListInteractor
import com.jarroyo.feature.schedules.api.model.User
import com.jarroyo.feature.schedules.ui.userselector.UserSelectorContract.Effect
import com.jarroyo.feature.schedules.ui.userselector.UserSelectorContract.Event
import com.jarroyo.feature.schedules.ui.userselector.UserSelectorContract.State
import com.jarroyo.library.navigation.api.navigator.AppNavigator
import com.jarroyo.library.ui.shared.BaseViewModel
import kotlinx.coroutines.launch
import kotlinx.serialization.json.Json
import org.koin.android.annotation.KoinViewModel

@KoinViewModel
class UserSelectorViewModel (
    private val appNavigator: AppNavigator,
    private val getUserListInteractor: GetUserListInteractor,
    savedStateHandle: SavedStateHandle,
) : BaseViewModel<Event, State, Effect>() {
    private val usersSelectedMap: MutableMap<String,User> = mutableMapOf()
    private val initialUsersSelected: List<User> = UserSelectorDestination.Arguments.getUserList(
        savedStateHandle,
    )?.run {
        if(isNotEmpty()) {
            Json.decodeFromString<List<User>>(this)
        } else {
            emptyList()
        }
    }.orEmpty()

    init {
        viewModelScope.launch {
            performSearch()
        }
    }

    override fun provideInitialState() = State(usersSelectedMap = usersSelectedMap.toMap())

    override fun handleEvent(event: Event) {
        when (event) {
            is Event.OnItemClicked -> handleOnItemClicked(event)
            is Event.OnSaveButtonClicked -> handleOnSaveButtonClicked()
            is Event.OnUpButtonClicked -> appNavigator.navigateUp()
        }
    }

    private fun handleOnItemClicked(event: Event.OnItemClicked) {
            updateUsersSelected(event)
            updateSaveButtonState()
    }

    private fun handleOnSaveButtonClicked() {
        sendEffect {
            Effect.SetResultAndNavigate(
                result = UserSelectorDestination.Result(
                   userList = usersSelectedMap.values.toList(),
                ),
                navigate = { appNavigator.navigateBack() },
            )
        }
    }

    private fun updateUsersSelected(event: Event.OnItemClicked) {
        if (event.selected) {
            usersSelectedMap[event.user.id] = event.user
        } else {
            usersSelectedMap.remove(event.user.id)
        }
        updateState { copy(usersSelectedMap = this@UserSelectorViewModel.usersSelectedMap.toMap()) }
    }

    private fun updateSaveButtonState() {
        updateState { copy(saveButtonEnabled = initialUsersSelected.toSet() != usersSelectedMap.values.toSet()) }
    }

    private suspend fun performSearch() {
        val result = getUserListInteractor()
        result.fold(
            success = { updateState { copy(users = it) }},
            failure = { sendEffect { Effect.ShowErrorSnackbar(it.message.orEmpty()) } },
        )
    }
}
