package com.jarroyo.feature.home.shared.ui

import androidx.compose.runtime.Composable
import com.jarroyo.library.feature.Feature
import com.jarroyo.library.navigation.api.destination.NavigationDestination
import org.koin.core.module.Module
import org.koin.dsl.module

class HomeFeature : Feature() {
    override val id = "Home"

    override val composableDestinations: Map<NavigationDestination, @Composable () -> Unit> = mapOf(
    )

    companion object {
        val module: Module = module {

        }
    }
}
