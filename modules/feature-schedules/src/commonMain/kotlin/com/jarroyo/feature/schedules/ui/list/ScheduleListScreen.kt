package com.jarroyo.feature.schedules.ui.list

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Scaffold
import androidx.compose.material.SnackbarDuration
import androidx.compose.material.SnackbarHost
import androidx.compose.material.SnackbarHostState
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import com.jarroyo.feature.schedules.ui.list.ScheduleListContract.Effect
import com.jarroyo.feature.schedules.ui.list.ScheduleListContract.Event
import com.jarroyo.feature.schedules.ui.list.ScheduleListContract.State
import com.jarroyo.library.ui.shared.theme.Spacing
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun ScheduleListScreen(viewModel: ScheduleListViewModel = koinViewModel<ScheduleListViewModel>()) {
    ScheduleListScreen(
        state = viewModel.viewState.value,
        sendEvent = { viewModel.onUiEvent(it) },
        effectFlow = viewModel.effect,
    )
}

@OptIn(ExperimentalMaterialApi::class)
@Composable
private fun ScheduleListScreen(
    state: State,
    effectFlow: Flow<Effect>,
    sendEvent: (event: Event) -> Unit,
) {
    val snackbarHostState = remember { SnackbarHostState() }
    LaunchedEffect(effectFlow) {
        effectFlow.onEach { effect ->
            when (effect) {
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
    ) { scaffoldPadding ->
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .verticalScroll(rememberScrollState())
                .padding(scaffoldPadding)
                .navigationBarsPadding(),
            verticalArrangement = Arrangement.spacedBy(Spacing.x01),
        ) {
            Text("${state.scheduleList}")
        }
    }
}

@Composable
private fun TopAppBar(
    sendEvent: (event: Event) -> Unit,
    state: State,
) {
    TopAppBar(
        title = { Text("Schedules") },
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
