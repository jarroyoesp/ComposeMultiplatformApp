package com.jarroyo.composeapp.ui.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.jarroyo.feature.home.shared.ui.RootView
import com.jarroyo.feature.home.shared.ui.rocketdetail.RocketDetailScreen
import com.jarroyo.library.navigation.api.destination.Screens

@Composable
fun NavGraph(navController: NavHostController) {
    NavHost(
        navController = navController,
        startDestination = Screens.HomeScreen.route,
        builder = {
            addComposableDestinations()
        },
    )
}

fun NavGraphBuilder.addComposableDestinations() {
    val homeDestination = Screens.HomeScreen.route
    val rocketDetailDestination = Screens.RocketDetailScreen.route
    composable(homeDestination) { RootView() }
    composable(rocketDetailDestination) { RocketDetailScreen() }
}
