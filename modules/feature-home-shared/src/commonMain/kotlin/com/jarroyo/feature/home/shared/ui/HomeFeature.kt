package com.jarroyo.feature.home.shared.ui

import androidx.compose.runtime.Composable
import com.jarroyo.feature.home.api.destination.HomeDestination
import com.jarroyo.feature.home.api.destination.LaunchDetailDestination
import com.jarroyo.feature.home.shared.ui.launchdetail.LaunchDetailScreen
import com.jarroyo.library.feature.Feature
import com.jarroyo.library.navigation.api.destination.NavigationDestination
import org.koin.core.module.Module
import org.koin.dsl.module


class HomeFeature : Feature() {
    override val id = "Home"

    override val composableDestinations: Map<NavigationDestination, @Composable () -> Unit> = mapOf(
         HomeDestination to { HomeScreen() },
         LaunchDetailDestination to { LaunchDetailScreen() },
    )

    override val navigationSuiteEntry: NavigationSuiteEntry = NavigationSuiteEntry(
        priority = 0,
        route = HomeDestination.route,
        label = "Home",
    )

    companion object {
        val module: Module = module {

        }
    }
}
