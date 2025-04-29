package com.jarroyo.feature.common

import androidx.compose.runtime.Composable
import com.jarroyo.feature.common.api.interactor.OpenUrlInBrowserInteractor
import com.jarroyo.feature.common.interactor.OpenUrlInBrowserInteractorImpl
import com.jarroyo.library.feature.Feature
import com.jarroyo.library.navigation.api.destination.NavigationDestination
import org.koin.core.module.Module
import org.koin.dsl.module

class CommonFeature : Feature() {
    override val id = "Common"

    override val composableDestinations: Map<NavigationDestination, @Composable () -> Unit> = mapOf(

    )

    companion object {
        val module: Module = module {
            factory<OpenUrlInBrowserInteractor> { OpenUrlInBrowserInteractorImpl() }
        }
    }
}
