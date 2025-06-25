package com.jarroyo.feature.schedules.ui.list

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListScope
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material.pullrefresh.PullRefreshIndicator
import androidx.compose.material.pullrefresh.pullRefresh
import androidx.compose.material.pullrefresh.rememberPullRefreshState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExtendedFloatingActionButton
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.onGloballyPositioned
import com.jarroyo.feature.schedules.api.destination.ScheduleDetailDestination
import com.jarroyo.feature.schedules.api.model.Schedule
import com.jarroyo.feature.schedules.ui.list.ScheduleListContract.Effect
import com.jarroyo.feature.schedules.ui.list.ScheduleListContract.Event
import com.jarroyo.feature.schedules.ui.list.ScheduleListContract.State
import com.jarroyo.library.ui.shared.component.EmptyStateWithImage
import com.jarroyo.library.ui.shared.component.LocalMainScaffoldPadding
import com.jarroyo.library.ui.shared.component.LocalNavHostController
import com.jarroyo.library.ui.shared.component.observeResult
import com.jarroyo.library.ui.shared.theme.Spacing
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun ScheduleListScreen(viewModel: ScheduleListViewModel = koinViewModel<ScheduleListViewModel>()) {
    LocalNavHostController.current.observeResult<ScheduleDetailDestination.Result>(ScheduleDetailDestination.route) { result ->
        viewModel.onUiEvent(Event.OnScheduleUpdated(result.operationType))
    }
    ScheduleListScreen(
        state = viewModel.viewState.value,
        sendEvent = { viewModel.onUiEvent(it) },
        effectFlow = viewModel.effect,
    )
}

@OptIn(ExperimentalMaterialApi::class, ExperimentalMaterial3Api::class)
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
    var fabHeight by remember { mutableIntStateOf(0) }
    val scrollState = rememberLazyListState()
    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior()
    val isTopBarVisible by remember { derivedStateOf { scrollBehavior.state.collapsedFraction < 0.5 } }
    Scaffold(
        topBar = { TopAppBar() },
        snackbarHost = { SnackbarHost(snackbarHostState) },
        modifier = Modifier.padding(LocalMainScaffoldPadding.current.value),
        floatingActionButton = {
            FloatingActionButton(
                sendEvent = sendEvent,
                expanded = isTopBarVisible,
                modifier = Modifier.onGloballyPositioned { fabHeight = it.size.height },
            )
        },
    ) { scaffoldPadding ->
        val pullRefreshState = rememberPullRefreshState(
            refreshing = state.loading,
            onRefresh = {
                sendEvent(Event.OnSwipeToRefresh)
            },
        )
        Box(
            modifier = Modifier
                .padding(scaffoldPadding)
                .pullRefresh(pullRefreshState),
        ) {
            PullRefreshIndicator(
                state.loading,
                pullRefreshState,
                Modifier.align(Alignment.TopCenter),
            )
            LazyColumn(
                modifier = Modifier.fillMaxSize(),
                state = scrollState,
                contentPadding = PaddingValues(Spacing.x02),
                verticalArrangement = Arrangement.spacedBy(Spacing.x01),
            ) {
                if (state.scheduleList.isNullOrEmpty() && state.loading) {
                    scheduleList(getScheduleListPlaceholderData(), sendEvent, placeholder = true)
                } else {
                    if (!state.scheduleList.isNullOrEmpty()) {
                        scheduleList(
                            data = state.scheduleList,
                            sendEvent = sendEvent,
                        )
                    } else {
                        item {
                            EmptyStateWithImage("No data available")
                        }
                    }
                }
            }
        }
    }
}

private fun LazyListScope.scheduleList(
    data: List<Schedule>,
    sendEvent: (event: Event) -> Unit,
    placeholder: Boolean = false,
) {
    items(data.size) { index ->
        ScheduleListItem(
            item = data[index],
            sendEvent = sendEvent,
            placeholder = placeholder,
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun TopAppBar() {
    TopAppBar(
        title = { Text("Schedules") },
    )
}

@Composable
private fun FloatingActionButton(
    sendEvent: (event: Event) -> Unit,
    expanded: Boolean,
    modifier: Modifier = Modifier,
) {
        ExtendedFloatingActionButton(
            text = { Text(text = "Add Schedule") },
            icon = {},
            onClick = { sendEvent(Event.OnAddScheduleButtonClicked) },
            modifier = modifier,
            expanded = expanded,
        )
}

private fun getScheduleListPlaceholderData(): List<Schedule> = List(6) {
    getPlaceholderData()
}

private fun getPlaceholderData(): Schedule = Schedule(
    id = "id",
    time = "9:00",
    slots = 4,
    users = emptyList(),
)
