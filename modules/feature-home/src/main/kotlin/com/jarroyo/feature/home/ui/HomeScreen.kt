package com.jarroyo.feature.home.ui

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListScope
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.pullrefresh.PullRefreshIndicator
import androidx.compose.material.pullrefresh.pullRefresh
import androidx.compose.material.pullrefresh.rememberPullRefreshState
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.SnackbarResult
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import com.jarroyo.composeapp.library.network.api.graphql.RocketsQuery
import com.jarroyo.feature.home.ui.HomeContract.Effect
import com.jarroyo.feature.home.ui.HomeContract.Event
import com.jarroyo.feature.home.ui.HomeContract.State
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import timber.log.Timber

@Composable
fun HomeScreen(
    viewModel: HomeViewModel = hiltViewModel(),
) {
    HomeScreen(
        state = viewModel.viewState.value,
        effectFlow = viewModel.effect,
        sendEvent = { viewModel.onUiEvent(it) },
    )
}

@Composable
private fun HomeScreen(
    state: State,
    effectFlow: Flow<Effect>,
    sendEvent: (event: Event) -> Unit,
) {
    val snackbarHostState = remember { SnackbarHostState() }
    val scrollState = rememberLazyListState()
    LaunchedEffect(effectFlow) {
        effectFlow.onEach { effect ->
            when (effect) {
                is Effect.ShowSnackbar -> launch {
                    snackbarHostState.showSnackbar(
                        message = effect.message,
                        duration = SnackbarDuration.Indefinite,
                    ).also { snackbarResult ->
                        if (snackbarResult == SnackbarResult.ActionPerformed) {
                            snackbarHostState.currentSnackbarData?.dismiss()
                        }
                    }
                }
            }
        }.collect()
    }
    Scaffold(
        modifier = Modifier.navigationBarsPadding(),
        snackbarHost = { SnackbarHost(snackbarHostState) },
    ) { scaffoldPadding ->
        val pullRefreshState = rememberPullRefreshState(
            refreshing = state.loading,
            onRefresh = {
                Timber.d("Swipe to refresh")
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
            PullRefreshIndicator(state.loading, pullRefreshState, Modifier.align(Alignment.TopCenter))
        }
    }
}

private fun LazyListScope.rocketList(
    data: List<RocketsQuery.Rocket>,
    sendEvent: (event: Event) -> Unit,
    placeholder: Boolean = false,
) {
    items(data) { item ->
        HomeRocketItem(item = item, sendEvent = sendEvent, placeholder = placeholder)
    }
}

@Suppress("MagicNumber")
private fun getRocketListPlaceholderData() = List(6) { getARocketPlaceholderData() }

private fun getARocketPlaceholderData() = RocketsQuery.Rocket(
    id = "id",
    name = "name",
    company = "Company",
    wikipedia = "Wiki",
)
