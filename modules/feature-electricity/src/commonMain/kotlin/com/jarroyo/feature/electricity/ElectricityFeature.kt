package com.jarroyo.feature.electricity

import androidx.compose.runtime.Composable
import com.jarroyo.feature.electricity.api.destination.ElectricityDestination
import com.jarroyo.feature.electricity.api.interactor.GetElectricityDataInteractor
import com.jarroyo.feature.electricity.di.electricityModule
import com.jarroyo.feature.electricity.ui.ElectricityViewModel
import com.jarroyo.feature.electricity.interactor.GetElectricityDataInteractorImpl
import com.jarroyo.feature.electricity.ui.ElectricityScreen
import com.jarroyo.library.feature.Feature
import com.jarroyo.library.navigation.api.destination.NavigationDestination
import org.koin.core.module.Module
import org.koin.core.module.dsl.factoryOf
import org.koin.dsl.module

class ElectricityFeature : Feature() {
    override val id = "Electricity"

    override val composableDestinations: Map<NavigationDestination, @Composable () -> Unit> = mapOf(
        ElectricityDestination to { ElectricityScreen() },
    )

    override val navigationSuiteEntry: NavigationSuiteEntry = NavigationSuiteEntry(
        priority = 0,
        route = ElectricityDestination.route,
        label = id,
    )

    companion object {
        val module: List<Module> = module {
            factory<GetElectricityDataInteractor> { GetElectricityDataInteractorImpl(get()) }
            factoryOf(::ElectricityViewModel)
        } + electricityModule()
    }
}
