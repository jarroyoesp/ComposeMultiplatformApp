package com.jarroyo.feature.home.shared.ui

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.compositionLocalOf
import androidx.compose.runtime.mutableStateOf
import co.touchlab.kermit.Logger
import com.jarroyo.feature.home.api.destination.HomeDestination
import com.jarroyo.feature.home.api.destination.LaunchDetailDestination
import com.jarroyo.feature.home.shared.ui.launchdetail.LaunchDetailScreen
import com.jarroyo.library.navigation.api.destination.NavigationDestination
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import com.jarroyo.library.navigation.api.navigator.AppNavigator
import com.jarroyo.library.navigation.api.navigator.NavigatorEvent
import moe.tlaster.precompose.PreComposeApp
import moe.tlaster.precompose.navigation.NavHost
import moe.tlaster.precompose.navigation.RouteBuilder
import moe.tlaster.precompose.navigation.rememberNavigator
import moe.tlaster.precompose.navigation.transition.NavTransition
import org.koin.compose.getKoin

internal val darkmodeState = mutableStateOf(false)
internal val safeAreaState = mutableStateOf(PaddingValues())
internal val SafeArea = compositionLocalOf { safeAreaState }
internal val DarkMode = compositionLocalOf { darkmodeState }

@Composable
fun RootView() {
    PreComposeApp {
        val appNavigator: AppNavigator = getKoin().get()
        val navigator = rememberNavigator()
        NavHost(
            navigator = navigator,
            navTransition = NavTransition(),
            initialRoute = HomeDestination().route,
        ) {
            addComposableDestinations()
        }
        LaunchedEffect(navigator) {
            appNavigator.destinations.onEach { event ->
                when (event) {
                    is NavigatorEvent.Directions -> navigator.navigate(
                        event.destination,
                        event.builder,
                    ).also { Logger.d("Navigate to ${event.destination}") }
                    is NavigatorEvent.NavigateBack -> navigator.goBack()
                    is NavigatorEvent.NavigateUp -> navigator.goBack()
                }
            }.launchIn(this)
        }
    }
}

private fun RouteBuilder.addComposableDestinations() {
    val composableDestinations: Map<NavigationDestination, @Composable (arguments: Map<String, String>) -> Unit> = mapOf(
        HomeDestination() to { HomeScreen() },
        LaunchDetailDestination() to { LaunchDetailScreen(it) },
    )
    composableDestinations.forEach { entry ->
        scene(
            route = entry.key.route,
            navTransition = NavTransition(),
        ) {
            entry.value(it.pathMap)
        }
    }
}
