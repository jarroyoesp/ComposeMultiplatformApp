package com.jarroyo.feature.home.shared.ui

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.compositionLocalOf
import androidx.compose.runtime.mutableStateOf
import co.touchlab.kermit.Logger
import com.jarroyo.feature.home.api.destination.HomeDestination
import com.jarroyo.feature.home.api.destination.LaunchDetailDestination
import com.jarroyo.feature.home.shared.ui.launchdetail.LaunchDetailScreen
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import com.jarroyo.library.navigation.api.navigator.AppNavigator
import com.jarroyo.library.navigation.api.navigator.NavigatorEvent
import org.koin.compose.getKoin
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import androidx.navigation.NavHostController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.compose.ui.Modifier

internal val darkmodeState = mutableStateOf(false)
internal val safeAreaState = mutableStateOf(PaddingValues())
internal val SafeArea = compositionLocalOf { safeAreaState }
internal val DarkMode = compositionLocalOf { darkmodeState }

@Composable
fun RootView() {
    val appNavigator: AppNavigator = getKoin().get()
    val navHostController: NavHostController = rememberNavController()
    Scaffold(modifier = Modifier.fillMaxSize()) { scaffoldPadding: PaddingValues ->
        NavHost(
            navController = navHostController,
            startDestination = HomeDestination.route,
            builder = {
                addComposableDestinations()
            },
        )
        LaunchedEffect(navHostController) {
            appNavigator.destinations.onEach { event ->
                when (event) {
                    is NavigatorEvent.Directions -> navHostController.navigate(
                        event.destination,
                        event.builder,
                    ).also { Logger.d("Navigate to ${event.destination}") }

                    is NavigatorEvent.NavigateBack -> navHostController.popBackStack()
                    is NavigatorEvent.NavigateUp -> navHostController.navigateUp()
                }
            }.launchIn(this)
        }
    }
}

fun NavGraphBuilder.addComposableDestinations() {
    composable(
        route = LaunchDetailDestination.route,
        arguments = LaunchDetailDestination.arguments
    ) { LaunchDetailScreen() }
    composable(
        route = HomeDestination.route,
        arguments = HomeDestination.arguments
    ) { HomeScreen() }
}
