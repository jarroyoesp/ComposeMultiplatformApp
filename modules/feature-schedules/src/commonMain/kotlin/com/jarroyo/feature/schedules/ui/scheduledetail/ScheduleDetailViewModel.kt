package com.jarroyo.feature.schedules.ui.scheduledetail

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import com.github.michaelbull.result.fold
import com.jarroyo.feature.schedules.api.destination.ScheduleDetailDestination
import com.jarroyo.feature.schedules.api.destination.UserSelectorDestination
import com.jarroyo.feature.schedules.api.interactor.AddScheduleInteractor
import com.jarroyo.feature.schedules.api.interactor.GetScheduleInteractor
import com.jarroyo.feature.schedules.api.interactor.GetUserInteractor
import com.jarroyo.feature.schedules.api.interactor.RemoveScheduleInteractor
import com.jarroyo.feature.schedules.api.model.Schedule
import com.jarroyo.feature.schedules.api.model.User
import com.jarroyo.feature.schedules.ui.scheduledetail.ScheduleDetailContract.Effect
import com.jarroyo.feature.schedules.ui.scheduledetail.ScheduleDetailContract.Event
import com.jarroyo.feature.schedules.ui.scheduledetail.ScheduleDetailContract.State
import com.jarroyo.library.navigation.api.navigator.AppNavigator
import com.jarroyo.library.ui.shared.BaseViewModel
import kotlinx.coroutines.launch
import kotlinx.serialization.json.Json
import org.koin.android.annotation.KoinViewModel

@KoinViewModel
class ScheduleDetailViewModel(
    private val addScheduleInteractor: AddScheduleInteractor,
    private val appNavigator: AppNavigator,
    private val getScheduleInteractor: GetScheduleInteractor,
    private val getUserInteractor: GetUserInteractor,
    private val removeScheduleInteractor: RemoveScheduleInteractor,
    savedStateHandle: SavedStateHandle,
) : BaseViewModel<Event, State, Effect>() {
    private var scheduleId: String? = ScheduleDetailDestination.Arguments.getId(savedStateHandle)

    init {
        if (!scheduleId.isNullOrEmpty()) {
            refreshData()
        }
    }

    override fun provideInitialState() = State()

    override fun handleEvent(event: Event) {
        when (event) {
            is Event.OnAddScheduleButtonClicked -> handleOnAddScheduleButtonClicked()
            is Event.OnUpButtonClicked -> appNavigator.navigateBack()
            is Event.OnIdValueChanged -> handleOnIdValueChanged(event.value)
            is Event.OnRemoveButtonClicked -> handleOnRemoveButtonClicked(checkNotNull(scheduleId))
            is Event.OnSlotsValueChanged -> handleOnSlotsValueChanged(event.value)
            is Event.OnTimeValueChanged -> handleOnTimeValueChanged(event.value)
            is Event.OnUserItemClicked -> handleOnUserItemClicked()
            is Event.OnUserSelectedUpdate -> handleOnUserSelectedUpdate(event.userList)
        }
    }

    private fun handleOnUserSelectedUpdate(userList: List<User>) {
        updateState { copy(userList = userList) }
        checkSaveButtonEnabled()
    }

    private fun handleOnUserItemClicked() {
        appNavigator.navigate(
            UserSelectorDestination.get(
                prepareParameterUserSelector(viewState.value.userList).orEmpty(),
            ),
        )
    }

    private fun handleOnIdValueChanged(id: String) {
        updateState { copy(id = id) }
        checkSaveButtonEnabled()
    }

    private fun handleOnSlotsValueChanged(slots: String) {
        updateState { copy(slots = slots) }
        checkSaveButtonEnabled()
    }

    private fun handleOnTimeValueChanged(time: String) {
        updateState { copy(time = time) }
        checkSaveButtonEnabled()
    }

    private fun handleOnRemoveButtonClicked(id: String) {
        viewModelScope.launch {
            val result = removeScheduleInteractor(id)
            result.fold(
                success = { Effect.SetResultAndNavigate(
                    result = ScheduleDetailDestination.Result(operationType = ScheduleDetailDestination.Result.OperationType.REMOVE),
                    navigate = { appNavigator.navigateBack() },
                )},
                failure = { sendEffect { Effect.ShowSnackbar(it.message.orEmpty()) } },
            )
        }
    }

    private fun handleOnAddScheduleButtonClicked() {
        viewModelScope.launch {
            val result = addScheduleInteractor(
                Schedule(
                    id = viewState.value.id.orEmpty(),
                    time = viewState.value.time.orEmpty(),
                    slots = viewState.value.slots?.toInt() ?: 0,
                    users = viewState.value.userList?.map { it.id }.orEmpty(),
                ),
            )
            result.fold(
                success = {
                    sendEffect {
                        Effect.SetResultAndNavigate(
                            result = ScheduleDetailDestination.Result(operationType = ScheduleDetailDestination.Result.OperationType.CREATE),
                            navigate = { appNavigator.navigateBack() },
                        )
                    }
                },
                failure = { sendEffect { Effect.ShowSnackbar(it.message.orEmpty()) } },
            )
        }
    }

    private fun refreshData() {
        viewModelScope.launch {
            updateState { copy(loading = true) }
            refreshSchedules()
            updateState { copy(loading = false) }
        }
    }

    private suspend fun refreshSchedules() {
        val result = getScheduleInteractor(checkNotNull(scheduleId))
        result.fold(
            success = {
                updateState {
                    copy(
                        editAllowed = true,
                        id = it?.id,
                        time = it?.time,
                        slots = it?.slots.toString(),
                    )
                }
                it?.users?.let { getUsers(it) }
            },
            failure = { sendEffect { Effect.ShowSnackbar(it.message.orEmpty()) } },
        )
    }

    private fun checkSaveButtonEnabled() {
        val fieldsRequired = !viewState.value.id.isNullOrEmpty()
                && !viewState.value.time.isNullOrEmpty()
                && !viewState.value.slots.isNullOrEmpty()
                && !viewState.value.userList.isNullOrEmpty()

        updateState { copy(saveButtonEnabled = fieldsRequired) }
    }

    private fun getUsers(userIdList: List<String>) {
        viewModelScope.launch {
            val userList = mutableListOf<User>()
            userIdList.forEach { userId ->
                val result = getUserInteractor(userId)
                result.fold(
                    success = { it?.let { userList.add(it) }},
                    failure = { },
                )
            }
            updateState { copy(userList = userList) }
        }
    }

    private fun prepareParameterUserSelector(list: List<User>?): String? = list?.let {
        Json.encodeToString(list)
    }
}
