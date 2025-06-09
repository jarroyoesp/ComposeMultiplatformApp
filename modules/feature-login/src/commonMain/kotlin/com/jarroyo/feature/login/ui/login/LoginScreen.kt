package com.jarroyo.feature.login.ui.login

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.jarroyo.feature.login.ui.login.LoginContract.Effect
import com.jarroyo.feature.login.ui.login.LoginContract.Event
import com.jarroyo.feature.login.ui.login.LoginContract.State
import com.jarroyo.library.ui.shared.component.LocalMainScaffoldPadding
import com.jarroyo.library.ui.shared.component.ProgressButton
import com.jarroyo.library.ui.shared.theme.Spacing
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun LoginScreen(viewModel: LoginViewModel = koinViewModel<LoginViewModel>()) {
    LoginScreen(
        effectFlow = viewModel.effect,
        sendEvent = { viewModel.onUiEvent(it) },
        state = viewModel.viewState.value,
    )
}

@Composable
private fun LoginScreen(
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

    val focusManager = LocalFocusManager.current
    Scaffold(
        snackbarHost = { SnackbarHost(snackbarHostState) },
        modifier = Modifier.padding(LocalMainScaffoldPadding.current.value),
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(Spacing.x02),
            verticalArrangement = Arrangement.spacedBy(Spacing.x01),
        ) {
            Text(
                text = "Login",
                fontSize = 25.sp,
                color = MaterialTheme.colorScheme.primary,
                fontWeight = FontWeight.Bold,
            )
            OutlinedTextField(
                value = state.email.orEmpty(),
                onValueChange = { value -> sendEvent(Event.OnEmailValueChanged(value)) },
                modifier = Modifier.fillMaxWidth(),
                label = { Text("Email") },
                singleLine = false,
            )
            OutlinedTextField(
                value = state.password.orEmpty(),
                onValueChange = { value -> sendEvent(Event.OnPasswordValueChanged(value)) },
                modifier = Modifier.fillMaxWidth(),
                label = { Text("Password") },
                singleLine = false,
            )
            ProgressButton(
                onClick = {
                    focusManager.clearFocus()
                    sendEvent(Event.OnLoginButtonClicked)
                },
                loading = state.loading,
                enabled = state.loginButtonEnabled,
                modifier = Modifier.fillMaxWidth(),
            ) {
                Text("LOGIN")
            }
        }
    }
}
