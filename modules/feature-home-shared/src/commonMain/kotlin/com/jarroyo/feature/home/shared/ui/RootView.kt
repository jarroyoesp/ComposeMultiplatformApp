package com.jarroyo.feature.home.shared.ui

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import co.touchlab.kermit.Logger
import com.jarroyo.feature.home.shared.ui.rocketdetail.RocketDetailScreen
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import com.jarroyo.library.navigation.api.navigator.AppNavigator
import com.jarroyo.library.navigation.api.navigator.NavigatorEvent
import com.jarroyo.library.navigation.di.NavigationKoinComponent
import moe.tlaster.precompose.PreComposeApp
import moe.tlaster.precompose.navigation.NavHost
import moe.tlaster.precompose.navigation.rememberNavigator
import moe.tlaster.precompose.navigation.transition.NavTransition

@Composable
fun RootView() {
    PreComposeApp {
        val appNavigator: AppNavigator = NavigationKoinComponent().appNavigator
        val navigator = rememberNavigator()
        NavHost(
            // Assign the navigator to the NavHost
            navigator = navigator,
            // Navigation transition for the scenes in this NavHost, this is optional
            navTransition = NavTransition(),
            // The start destination
            initialRoute = "/home",
        ) {
            // Define a scene to the navigation graph
            scene(
                // Scene's route path
                route = "/home",
                // Navigation transition for this scene, this is optional
                navTransition = NavTransition(),
            ) {
                HomeScreen()
            }
            scene(
                // Scene's route path
                route = "/rocket_detail_screen",
                // Navigation transition for this scene, this is optional
                navTransition = NavTransition(),
            ) {
                RocketDetailScreen()
            }
        }
        LaunchedEffect(navigator) {
            appNavigator.destinations.onEach { event ->
                when (event) {
                    is NavigatorEvent.Directions -> navigator.navigate(
                        event.destination,
                        event.builder,
                    ).also { Logger.d("Navigate to ${event.destination}") }
                    // is NavigatorEvent.HandleDeepLink -> TODO()
                    is NavigatorEvent.NavigateBack -> navigator.goBack()
                    is NavigatorEvent.NavigateUp -> navigator.goBack()
                }
            }.launchIn(this)
        }
    }
}
