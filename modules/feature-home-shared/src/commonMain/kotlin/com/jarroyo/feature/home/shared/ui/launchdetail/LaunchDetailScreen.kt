package com.jarroyo.feature.home.shared.ui.launchdetail

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Button
import androidx.compose.material.CircularProgressIndicator
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
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.jarroyo.composeapp.library.network.api.graphql.fragment.LaunchFragment
import com.jarroyo.composeapp.library.network.api.graphql.fragment.RocketFragment
import com.jarroyo.feature.home.shared.di.FeatureHomeKoinComponent
import com.jarroyo.feature.home.shared.ui.launchdetail.LaunchDetailContract.Effect
import com.jarroyo.feature.home.shared.ui.launchdetail.LaunchDetailContract.Event
import com.jarroyo.feature.home.shared.ui.launchdetail.LaunchDetailContract.State
import com.jarroyo.library.navigation.di.NavigationKoinComponent
import com.jarroyo.library.ui.shared.component.placeholder
import com.jarroyo.library.ui.shared.theme.Spacing
import io.kamel.image.KamelImage
import io.kamel.image.asyncPainterResource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import kotlinx.datetime.Clock
import moe.tlaster.precompose.viewmodel.viewModel

@Composable
fun LaunchDetailScreen(
    arguments: Map<String, String>? = null,
    viewModel: LaunchDetailViewModel = viewModel(modelClass = LaunchDetailViewModel::class) {
        LaunchDetailViewModel(
            FeatureHomeKoinComponent().addFavoriteInteractor,
            NavigationKoinComponent().appNavigator,
            FeatureHomeKoinComponent().getFavoritesInteractor,
            FeatureHomeKoinComponent().getLaunchDetailInteractor,
            FeatureHomeKoinComponent().openUrlInBrowserInteractor,
            FeatureHomeKoinComponent().removeFavoriteInteractor,
        )
    },
) {
    LaunchedEffect(Unit) {
        arguments?.get("id")?.let {
            viewModel.onUiEvent(Event.OnViewAttached(it))
        }
    }

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
            if (state.loading) {
                DetailItem(getPlaceholderData(), sendEvent = {}, true)
            } else {
                DetailItem(state.launch, sendEvent)
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
            modifier = Modifier.placeholder(placeholder).clickable { sendEvent(Event.OnOpenUrl(launch?.links?.article_link.orEmpty())) },
        )
        KamelImage(
            resource = asyncPainterResource(
                launch?.links?.flickr_images?.firstOrNull().orEmpty(),
            ),
            contentDescription = null,
            modifier = Modifier.fillMaxWidth().placeholder(placeholder),
            alignment = Alignment.TopCenter,
            onLoading = { CircularProgressIndicator() },
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
    Button(
        onClick = { sendEvent(Event.OnAddFavoritesButtonClicked) },
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = Spacing.x01)
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
