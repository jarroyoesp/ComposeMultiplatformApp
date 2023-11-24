package com.jarroyo.feature.home.shared.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.calculateEndPadding
import androidx.compose.foundation.layout.calculateStartPadding
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListScope
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.DrawerValue
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.SnackbarDuration
import androidx.compose.material.SnackbarHost
import androidx.compose.material.SnackbarHostState
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.pullrefresh.PullRefreshIndicator
import androidx.compose.material.pullrefresh.pullRefresh
import androidx.compose.material.pullrefresh.rememberPullRefreshState
import androidx.compose.material.rememberDrawerState
import androidx.compose.material.rememberScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp
import com.jarroyo.composeapp.library.network.api.graphql.RocketsQuery
import com.jarroyo.feature.home.shared.SafeArea
import com.jarroyo.feature.home.shared.di.FeatureHomeKoinComponent
import com.jarroyo.feature.home.shared.ui.HomeContract.Effect
import com.jarroyo.feature.home.shared.ui.HomeContract.Event
import com.jarroyo.feature.home.shared.ui.HomeContract.State
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch

@Composable
fun HomeScreen(viewModel: HomeViewModel = FeatureHomeKoinComponent().homeViewModel) {
    HomeScreen(
        state = viewModel.viewState.value,
        sendEvent = { viewModel.onUiEvent(it) },
        effectFlow = viewModel.effect,
    )
}

@OptIn(ExperimentalMaterialApi::class)
@Composable
private fun HomeScreen(
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

    val scope = rememberCoroutineScope()
    val scaffoldState = rememberScaffoldState(rememberDrawerState(DrawerValue.Closed))
    val scrollState = rememberLazyListState()
    Scaffold(
        topBar = {
            Box(modifier = Modifier.background(MaterialTheme.colors.primary)) {
                TopAppBar(
                    modifier = Modifier.padding(
                        start = SafeArea.current.value.calculateStartPadding(LayoutDirection.Ltr),
                        top = SafeArea.current.value.calculateTopPadding(),
                        end = SafeArea.current.value.calculateEndPadding(LayoutDirection.Ltr),
                    ),
                    title = { Text(state.text) },
                    navigationIcon = {
                        Icon(
                            Icons.Default.Menu,
                            "test",
                            modifier = Modifier.clickable(
                                onClick = {
                                    scope.launch { scaffoldState.drawerState.open() }
                                },
                            ),
                        )
                    },
                )
            }
        },
        scaffoldState = scaffoldState,
        snackbarHost = { SnackbarHost(snackbarHostState) },
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
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(scaffoldPadding),
                state = scrollState,
                contentPadding = PaddingValues(16.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp),
            ) {
                if (!state.rocketList.isNullOrEmpty() || state.loading) {
                    if (state.rocketList.isNullOrEmpty() && state.loading) {
                        rocketList(getRocketListPlaceholderData(), sendEvent, placeholder = true)
                    } else {
                        state.rocketList?.let { rockets ->
                            rocketList(rockets, sendEvent)
                        }
                    }
                }
            }
            PullRefreshIndicator(
                state.loading,
                pullRefreshState,
                Modifier.align(Alignment.TopCenter),
            )
        }
    }
}

private fun LazyListScope.rocketList(
    data: List<RocketsQuery.Rocket>,
    sendEvent: (event: Event) -> Unit,
    placeholder: Boolean = false,
) {
    items(data.size) { index ->
        HomeRocketItem(item = data[index], sendEvent = sendEvent, placeholder = placeholder)
    }
}

private fun getRocketListPlaceholderData(): List<RocketsQuery.Rocket> = List(6) {
    RocketsQuery.Rocket(
        company = "company",
        name = "name",
        id = "id",
        wikipedia = null,
    )
}
