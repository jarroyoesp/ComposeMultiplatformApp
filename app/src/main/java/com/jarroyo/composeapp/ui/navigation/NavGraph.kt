package com.jarroyo.composeapp.ui.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.jarroyo.feature.home.HomeScreen

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
    val destination = Screens.HomeScreen.route
    composable(destination) { HomeScreen() }
}
