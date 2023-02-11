package com.jarroyo.feature.home

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.jarroyo.feature.home.HomeContract.Event
import com.jarroyo.feature.home.HomeContract.State

@Composable
fun HomeScreen(viewModel: HomeViewModel = hiltViewModel()) {
    HomeScreen(
        state = viewModel.viewState.value,
        sendEvent = { viewModel.onUiEvent(it) },
    )
}

@Composable
private fun HomeScreen(
    state: State,
    sendEvent: (event: Event) -> Unit,
) {
    Column(modifier = Modifier.padding(16.dp)) {
        Text(text = "HomeScreen! ${state.text}")
        Button(onClick = { sendEvent(Event.OnAcceptButtonClicked) }) {
            Text(text = "Accept")
        }
    }
}
