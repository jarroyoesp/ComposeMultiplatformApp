package com.jarroyo.feature.launches.ui.launchdetail

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import coil3.compose.AsyncImage
import com.jarroyo.composeapp.library.network.api.graphql.fragment.LaunchFragment
import com.jarroyo.composeapp.library.network.api.graphql.fragment.RocketFragment
import com.jarroyo.feature.launches.api.destination.LaunchDestination
import com.jarroyo.feature.launches.ui.launchdetail.LaunchDetailContract.Effect
import com.jarroyo.feature.launches.ui.launchdetail.LaunchDetailContract.Event
import com.jarroyo.feature.launches.ui.launchdetail.LaunchDetailContract.State
import com.jarroyo.library.ui.shared.component.EmptyStateWithImage
import com.jarroyo.library.ui.shared.component.LocalNavHostController
import com.jarroyo.library.ui.shared.component.placeholder
import com.jarroyo.library.ui.shared.component.setResult
import com.jarroyo.library.ui.shared.theme.Spacing
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import kotlinx.datetime.Clock
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun LaunchDetailScreen(viewModel: LaunchDetailViewModel = koinViewModel<LaunchDetailViewModel>()) {
    LaunchDetailScreen(
        effectFlow = viewModel.effect,
        sendEvent = { viewModel.onUiEvent(it) },
        state = viewModel.viewState.value,
    )
}

@Composable
private fun LaunchDetailScreen(
    effectFlow: Flow<Effect>,
    sendEvent: (event: Event) -> Unit,
    state: State,
) {
    val navHostController = LocalNavHostController.current
    val snackbarHostState = remember { SnackbarHostState() }
    LaunchedEffect(effectFlow) {
        effectFlow.onEach { effect ->
            when (effect) {
                is Effect.SetResultAndNavigate -> {
                    navHostController.setResult(LaunchDestination.route, effect.result)
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
        bottomBar = { BottomBar(state, sendEvent) },
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
            if (state.loading && state.launch == null) {
                DetailItem(getPlaceholderData(), sendEvent = {}, true)
            } else {
                if (state.launch != null) {
                    DetailItem(state.launch, sendEvent)
                } else {
                    EmptyStateWithImage("NO data available")
                }
            }
        }
    }
}

@Composable
private fun DetailItem(
    launch: LaunchFragment?,
    sendEvent: (event: Event) -> Unit,
    placeholder: Boolean = false,
) {
    Column(
        modifier = Modifier.padding(Spacing.x02),
        verticalArrangement = Arrangement.spacedBy(Spacing.x01),
    ) {
        Text(
            text = launch?.details.orEmpty(),
            modifier = Modifier.placeholder(placeholder),
        )
        Text(
            text = launch?.links?.article_link.orEmpty(),
            modifier = Modifier.placeholder(placeholder)
                .clickable { sendEvent(Event.OnOpenUrl(launch?.links?.article_link.orEmpty())) },
        )
        AsyncImage(
            model = launch?.links?.flickr_images?.firstOrNull().orEmpty(),
            contentDescription = "",
        )
    }
}

internal fun getPlaceholderData(): LaunchFragment = LaunchFragment(
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

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun TopAppBar(
    sendEvent: (event: Event) -> Unit,
    state: State,
) {
    TopAppBar(
        title = { Text(state.launch?.mission_name.orEmpty()) },
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
    if (state.favorite != null) {
        Button(
            onClick = { sendEvent(Event.OnAddFavoritesButtonClicked) },
            modifier = Modifier
                .fillMaxWidth()
                .padding(Spacing.x02)
                .navigationBarsPadding(),
            enabled = !state.loading,
        ) {
            if (state.favorite) {
                Text("Remove from Favorites")
            } else {
                Text("Add to Favorites")
            }
        }
    }
}
