package com.jarroyo.feature.launches.ui.launchlist

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
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
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import com.jarroyo.composeapp.library.network.api.graphql.fragment.LaunchFragment
import com.jarroyo.composeapp.library.network.api.graphql.fragment.RocketFragment
import com.jarroyo.feature.launches.api.destination.LaunchDestination
import com.jarroyo.feature.launches.ui.launchlist.LaunchListContract.Effect
import com.jarroyo.feature.launches.ui.launchlist.LaunchListContract.Event
import com.jarroyo.feature.launches.ui.launchlist.LaunchListContract.State
import com.jarroyo.library.ui.shared.component.LocalMainScaffoldPadding
import com.jarroyo.library.ui.shared.component.LocalNavHostController
import com.jarroyo.library.ui.shared.component.observeResult
import com.jarroyo.library.ui.shared.theme.Spacing
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import kotlinx.datetime.Clock
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun LaunchListScreen(viewModel: LaunchListViewModel = koinViewModel<LaunchListViewModel>()) {
    LocalNavHostController.current.observeResult<LaunchDestination.Result>(LaunchDestination.route) { result ->
        viewModel.onUiEvent(Event.OnLaunchUpdated(result.type, result.name))
    }

    LaunchListScreen(
        state = viewModel.viewState.value,
        sendEvent = { viewModel.onUiEvent(it) },
        effectFlow = viewModel.effect,
    )
}

@OptIn(ExperimentalMaterialApi::class, ExperimentalMaterial3Api::class)
@Composable
private fun LaunchListScreen(
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
    val scrollState = rememberLazyListState()
    Scaffold(
        modifier = Modifier.padding(LocalMainScaffoldPadding.current.value),
        topBar = {
                TopAppBar(
                    title = { Text("Space X launches") },
                )
        },
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
                if (state.rocketList.isNullOrEmpty() && state.loading) {
                    rocketList(getLaunchListPlaceholderData(), sendEvent, placeholder = true)
                } else {
                    if (!state.rocketList.isNullOrEmpty()) {
                        rocketList(
                            data = state.rocketList,
                            sendEvent = sendEvent,
                            favoritesList = state.favoritesList,
                        )
                    } else {
                        item {
                            Text(
                                text = "NO data available",
                                modifier = Modifier.fillMaxWidth(),
                                textAlign = TextAlign.Center,
                            )
                        }
                    }
                }
            }
        }
    }
}

private fun LazyListScope.rocketList(
    data: List<LaunchFragment>,
    sendEvent: (event: Event) -> Unit,
    favoritesList: List<String>? = null,
    placeholder: Boolean = false,
) {
    items(data.size) { index ->
        LaunchListItem(
            item = data[index],
            sendEvent = sendEvent,
            favoritesList = favoritesList,
            placeholder = placeholder,
        )
    }
}

private fun getLaunchListPlaceholderData(): List<LaunchFragment> = List(6) {
    getPlaceholderData()
}

private fun getPlaceholderData(): LaunchFragment = LaunchFragment(
    id = "id",
    details = "details",
    mission_name = "Lorem Ipsum",
    launch_date_local = Clock.System.now(),
    rocket = LaunchFragment.Rocket(
        rocket = LaunchFragment.Rocket1(
            __typename = "",
            RocketFragment(
                company = "company",
                name = "name",
                id = "id",
                wikipedia = null,
                active = true,
            ),

            ),
    ),
    links = null,
)
