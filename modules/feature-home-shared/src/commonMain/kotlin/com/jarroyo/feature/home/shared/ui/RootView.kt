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
import com.jarroyo.library.feature.Feature

internal val darkmodeState = mutableStateOf(false)
internal val safeAreaState = mutableStateOf(PaddingValues())
internal val SafeArea = compositionLocalOf { safeAreaState }
internal val DarkMode = compositionLocalOf { darkmodeState }

@Composable
fun RootView() {
    val appNavigator: AppNavigator = getKoin().get()
    val navHostController: NavHostController = rememberNavController()
    val features: List<Feature> = getKoin().get<List<Feature>>()
    val mainNavigationBarEntries: Map<String, Feature.NavigationSuiteEntry> = populateNavigationBar(features)

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        bottomBar = { MainNavigationBar(navHostController, mainNavigationBarEntries) },
    ) { scaffoldPadding: PaddingValues ->
        NavHost(
            navController = navHostController,
            startDestination = HomeDestination.route,
            builder = {
                addComposableDestinations(features = features)
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

fun NavGraphBuilder.addComposableDestinations(features: List<Feature>) {
    features.forEach { feature ->
        feature.composableDestinations.forEach { entry ->
            val destination = entry.key
            composable(destination.route, destination.arguments) { entry.value() }
        }
    }
}
private fun populateNavigationBar(features: List<Feature>): Map<String, Feature.NavigationSuiteEntry> {
    val navigationEntryMap = mutableMapOf<String, Feature.NavigationSuiteEntry>()
    features
        .filter { it.navigationSuiteEntry?.enabled == true }
        .forEach { feature ->
            feature.navigationSuiteEntry?.let { entry -> navigationEntryMap[entry.route] = entry }
        }
    return navigationEntryMap
}
