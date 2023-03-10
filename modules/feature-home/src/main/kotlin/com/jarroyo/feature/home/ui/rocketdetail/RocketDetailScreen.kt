package com.jarroyo.feature.home.ui.rocketdetail

import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.SnackbarResult
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.hilt.navigation.compose.hiltViewModel
import com.jarroyo.feature.home.ui.HomeContract.Effect
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch

@Composable
fun RocketDetailScreen(viewModel: RocketDetailViewModel = hiltViewModel()) {
    RocketDetailScreen(
        effectFlow = viewModel.effect,
    )
}

@Composable
private fun RocketDetailScreen(
    effectFlow: Flow<Effect>,
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
    Text(text = "Rocket Detail")
}
