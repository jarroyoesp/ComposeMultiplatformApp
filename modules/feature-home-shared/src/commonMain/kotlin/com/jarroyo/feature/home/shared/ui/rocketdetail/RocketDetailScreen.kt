package com.jarroyo.feature.home.shared.ui.rocketdetail

import androidx.compose.material.Button
import androidx.compose.material.IconButton
import androidx.compose.material.Scaffold
import androidx.compose.material.SnackbarDuration
import androidx.compose.material.SnackbarHostState
import androidx.compose.material.SnackbarResult
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import com.jarroyo.feature.home.shared.di.FeatureHomeKoinComponent
import com.jarroyo.feature.home.shared.ui.rocketdetail.RocketDetailContract.Effect
import com.jarroyo.feature.home.shared.ui.rocketdetail.RocketDetailContract.Event
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch

@Composable
fun RocketDetailScreen(viewModel: RocketDetailViewModel = FeatureHomeKoinComponent().rocketDetailViewModel) {
    RocketDetailScreen(
        effectFlow = viewModel.effect,
        sendEvent = { viewModel.onUiEvent(it) },
    )
}

@Composable
private fun RocketDetailScreen(
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
        topBar = {
            TopAppBar(
                title = {},
                navigationIcon =
                {
                    IconButton(
                        onClick = { sendEvent(Event.OnUpButtonClicked)},
                    ) {
                    }
                },
            )
        },
    ) { scaffoldPadding ->
        Text(text = "Rocket Detail")
        Button(onClick = {sendEvent(Event.OnUpButtonClicked)}) {
            Text("Navigate Back")
        }
    }
}
