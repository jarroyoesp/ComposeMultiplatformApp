package com.jarroyo.library.feature

import androidx.compose.runtime.Composable
import com.jarroyo.library.navigation.api.destination.NavigationDestination

abstract class Feature {
    abstract val id: String
    open val bottomSheetDestinations: Map<NavigationDestination, @Composable () -> Unit> = emptyMap()
    open val composableDestinations: Map<NavigationDestination, @Composable () -> Unit> = emptyMap()
    open val debugComposable: @Composable (() -> Unit)? = null
    open val dialogDestinations: Map<NavigationDestination, @Composable () -> Unit> = emptyMap()
    open val featureLifecycle: FeatureLifecycle = FeatureLifecycle()
    open val navigationSuiteEntry: NavigationSuiteEntry? = null

    data class NavigationSuiteEntry(
        val priority: Int,
        val route: String,
        val label: String,
        val enabled: Boolean = true,
        val hideBottomNav: Boolean = false,
    )
}
