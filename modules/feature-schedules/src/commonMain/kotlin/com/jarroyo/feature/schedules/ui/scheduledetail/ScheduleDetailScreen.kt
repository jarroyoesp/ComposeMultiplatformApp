package com.jarroyo.feature.schedules.ui.scheduledetail

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.InputChip
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextOverflow
import com.jarroyo.feature.schedules.api.destination.ScheduleDetailDestination
import com.jarroyo.feature.schedules.api.destination.UserSelectorDestination
import com.jarroyo.feature.schedules.api.ext.getFullName
import com.jarroyo.feature.schedules.api.model.User
import com.jarroyo.feature.schedules.ui.scheduledetail.ScheduleDetailContract.Effect
import com.jarroyo.feature.schedules.ui.scheduledetail.ScheduleDetailContract.Event
import com.jarroyo.feature.schedules.ui.scheduledetail.ScheduleDetailContract.State
import com.jarroyo.library.ui.shared.component.LocalMainScaffoldPadding
import com.jarroyo.library.ui.shared.component.LocalNavHostController
import com.jarroyo.library.ui.shared.component.observeResult
import com.jarroyo.library.ui.shared.component.placeholder
import com.jarroyo.library.ui.shared.component.setResult
import com.jarroyo.library.ui.shared.resources.SharedResources
import com.jarroyo.library.ui.shared.theme.Spacing
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import org.jetbrains.compose.resources.painterResource
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun ScheduleDetailScreen(viewModel: ScheduleDetailViewModel = koinViewModel<ScheduleDetailViewModel>()) {
    LocalNavHostController.current.observeResult<UserSelectorDestination.Result>(
        UserSelectorDestination.route,
    ) { result ->
        viewModel.onUiEvent(Event.OnUserSelectedUpdate(result.userList))
    }
    ScheduleDetailScreen(
        state = viewModel.viewState.value,
        sendEvent = { viewModel.onUiEvent(it) },
        effectFlow = viewModel.effect,
    )
}

@Composable
private fun ScheduleDetailScreen(
    state: State,
    effectFlow: Flow<Effect>,
    sendEvent: (event: Event) -> Unit,
) {
    val snackbarHostState = remember { SnackbarHostState() }
    val navHostController = LocalNavHostController.current
    LaunchedEffect(effectFlow) {
        effectFlow.onEach { effect ->
            when (effect) {
                is Effect.SetResultAndNavigate -> {
                    navHostController.setResult(ScheduleDetailDestination.route, effect.result)
                    effect.navigate()
                }

                is Effect.ShowSnackbar -> launch {
                    snackbarHostState.showSnackbar(
                        message = effect.message,
                        duration = SnackbarDuration.Short,
                    )
                }
            }
        }.collect()
    }
    Scaffold(
        topBar = { TopAppBar(sendEvent, state) },
        snackbarHost = { SnackbarHost(snackbarHostState) },
        modifier = Modifier.padding(LocalMainScaffoldPadding.current.value),
    ) { scaffoldPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(scaffoldPadding)
                .padding(horizontal = Spacing.x02),
            verticalArrangement = Arrangement.spacedBy(Spacing.x01),
        ) {
            OutlinedTextField(
                value = state.id.orEmpty(),
                onValueChange = { value -> sendEvent(Event.OnIdValueChanged(value)) },
                modifier = Modifier.fillMaxWidth(),
                label = { Text("id") },
                singleLine = false,
            )
            OutlinedTextField(
                value = state.time.orEmpty(),
                onValueChange = { value -> sendEvent(Event.OnTimeValueChanged(value)) },
                modifier = Modifier.fillMaxWidth(),
                label = { Text("Time") },
                singleLine = false,
            )
            OutlinedTextField(
                value = state.slots.orEmpty(),
                onValueChange = { value -> sendEvent(Event.OnSlotsValueChanged(value)) },
                modifier = Modifier.fillMaxWidth(),
                label = { Text("Slots") },
                singleLine = false,
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
            )
            UserListItem(
                userList = state.userList.orEmpty(),
                sendEvent = sendEvent,
            )
            Button(
                onClick = { sendEvent(Event.OnAddScheduleButtonClicked) },
                modifier = Modifier.fillMaxWidth(),
                enabled = state.saveButtonEnabled,
                colors = ButtonDefaults.buttonColors(
                    containerColor = MaterialTheme.colorScheme.primary,
                    contentColor = MaterialTheme.colorScheme.onPrimary,
                ),
            ) {
                val text = if (state.editAllowed) {
                    "Update schedule"
                } else {
                    "Add schedule"
                }
                Text(text)
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun TopAppBar(
    sendEvent: (event: Event) -> Unit,
    state: State,
) {
    TopAppBar(
        title = { Text("Schedule Detail") },
        navigationIcon =
            {
                IconButton(
                    onClick = { sendEvent(Event.OnUpButtonClicked) },
                ) {
                    Icon(
                        imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                        contentDescription = null,
                    )
                }
            },
        actions = {
            if (state.editAllowed) {
                IconButton(
                    onClick = { sendEvent(Event.OnRemoveButtonClicked) },
                ) {
                    Icon(
                        painter = painterResource(SharedResources.ui_ic_delete),
                        contentDescription = null,
                    )
                }
            }
        },
    )
}

@Composable
fun UserListItem(
    userList: List<User>,
    sendEvent: (event: Event) -> Unit,
    modifier: Modifier = Modifier,
    placeholder: Boolean = false,
) {
    Column(
        verticalArrangement = Arrangement.spacedBy(Spacing.half),
    ){
        Text("Participants")
        FlowRow(
            modifier = modifier,
            verticalArrangement = Arrangement.Center,
            horizontalArrangement = Arrangement.spacedBy(Spacing.quarter),
        ) {
            for (user in userList) {
                UserInputChipItem(
                    text = user.getFullName(),
                    onclick = { sendEvent(Event.OnUserItemClicked) },
                    placeholder = placeholder,
                )
            }
            UserInputChipItem(
                text = "Add more",
                onclick = { sendEvent(Event.OnUserItemClicked) },
                placeholder = placeholder,
            )
        }
    }
}

@Composable
fun UserInputChipItem(
    text: String,
    onclick: () -> Unit,
    modifier: Modifier = Modifier,
    placeholder: Boolean = false,
) {
    if (text.isNotEmpty()) {
        Box(
            modifier = modifier,
            contentAlignment = Alignment.Center,
        ) {
            InputChip(
                selected = false,
                onClick = onclick,
                label = {
                    Text(
                        text = text,
                        overflow = TextOverflow.Ellipsis,
                        maxLines = 1,
                    )
                },
                modifier = Modifier.placeholder(placeholder),
                leadingIcon = {
                    Icon(
                        painter = painterResource(SharedResources.ui_ic_user),
                        contentDescription = null,
                    )
                },
            )
        }
    }
}
