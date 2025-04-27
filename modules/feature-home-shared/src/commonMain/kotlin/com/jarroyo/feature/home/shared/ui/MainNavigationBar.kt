package com.jarroyo.feature.home.shared.ui

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarDefaults
import androidx.compose.material3.NavigationBarItemColors
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.contentColorFor
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.getValue
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.apollographql.apollo.api.label
import com.jarroyo.feature.schedules.navigationsuite.ScheduleListNavigationBarItem
import com.jarroyo.library.feature.Feature

@Composable
fun MainNavigationBar(
    navController: NavHostController,
    mainNavigationBarEntries: Map<String, Feature.NavigationSuiteEntry>,
    containerColor: Color = NavigationBarDefaults.containerColor,
    contentColor: Color = MaterialTheme.colorScheme.contentColorFor(containerColor),
    tonalElevation: Dp = NavigationBarDefaults.Elevation,
    itemColors: NavigationBarItemColors = NavigationBarItemDefaults.colors(),
) {
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.run { destination.route }
    val hideBottomNav = mainNavigationBarEntries[currentRoute]?.hideBottomNav != false

    if (!hideBottomNav) {
        NavigationBar(
            containerColor = containerColor,
            contentColor = contentColor,
            tonalElevation = tonalElevation,
        ) {
            mainNavigationBarEntries.values.forEach { entry ->
                ScheduleListNavigationBarItem(
                    selected = currentRoute == entry.route,
                    onClick = {
                        navController.navigate(entry.route) {
                            if (!entry.hideBottomNav) {
                                restoreState = true
                                popUpTo(0) {
                                    inclusive = true
                                    saveState = true
                                }
                                launchSingleTop = true
                            }
                        }
                    },
                    label = entry.label,
                )
            }
        }
    }
}