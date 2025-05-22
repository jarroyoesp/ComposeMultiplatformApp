package com.jarroyo.feature.schedules.ui.userselector

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Divider
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.Button
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import com.jarroyo.feature.schedules.api.destination.UserSelectorDestination
import com.jarroyo.feature.schedules.ui.userselector.UserSelectorContract.Effect
import com.jarroyo.feature.schedules.ui.userselector.UserSelectorContract.Event
import com.jarroyo.feature.schedules.ui.userselector.UserSelectorContract.State
import com.jarroyo.library.ui.shared.component.BottomButtonBar
import com.jarroyo.library.ui.shared.component.LocalNavHostController
import com.jarroyo.library.ui.shared.component.setResult
import com.jarroyo.library.ui.shared.theme.Spacing
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.onEach
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun UserSelectorScreen(viewModel: UserSelectorViewModel = koinViewModel<UserSelectorViewModel>()) {
    UserSelectorScreen(
        state = viewModel.viewState.value,
        sendEvent = { viewModel.onUiEvent(it) },
        effectFlow = viewModel.effect,
    )
}

@Composable
private fun UserSelectorScreen(
    state: State,
    effectFlow: Flow<Effect>,
    sendEvent: (event: Event) -> Unit,
) {
    val navHostController = LocalNavHostController.current
    val snackbarHostState = remember { SnackbarHostState() }
    LaunchedEffect(effectFlow) {
        effectFlow.onEach { effect ->
            when (effect) {
                is Effect.SetResultAndNavigate -> {
                    navHostController.setResult(UserSelectorDestination.route, effect.result)
                    effect.navigate()
                }

                is Effect.ShowErrorSnackbar -> snackbarHostState.showSnackbar(
                    message = effect.message,
                    duration = SnackbarDuration.Short,
                )
            }
        }.collect()
    }
    Scaffold(
        modifier = Modifier,
        topBar = { TopAppBar(sendEvent) },
        bottomBar = { BottomBar(state, sendEvent) },
        snackbarHost = {
            SnackbarHost(
                hostState = snackbarHostState,
                modifier = if (!state.multipleSelection) {
                    Modifier.navigationBarsPadding()
                } else {
                    Modifier
                },
            )
        },
    ) { scaffoldPadding ->
        Box(
            modifier = Modifier
                .padding(scaffoldPadding),
        ) {
            LazyColumn(
                modifier = Modifier.fillMaxSize(),
                contentPadding = PaddingValues(Spacing.x02),
                verticalArrangement = Arrangement.spacedBy(Spacing.x01),
            ) {
                items(state.users.orEmpty()) { item ->
                    UserSelectorItem(
                        item,
                        state,
                        onClick = { sendEvent(Event.OnItemClicked(item, it)) })
                    Divider()
                }
            }
        }
    }
}

@Composable
private fun TopAppBar(
    sendEvent: (event: Event) -> Unit,
) {
    androidx.compose.material.TopAppBar(
        title = { Text("User selector") },
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
    )
}

@Composable
private fun BottomBar(
    state: State,
    sendEvent: (event: Event) -> Unit,
) {
    BottomButtonBar {
        Button(
            onClick = { sendEvent(Event.OnSaveButtonClicked) },
            modifier = Modifier
                .fillMaxWidth()
                .navigationBarsPadding(),
            enabled = state.saveButtonEnabled,
        ) { Text("Save") }
    }
}
