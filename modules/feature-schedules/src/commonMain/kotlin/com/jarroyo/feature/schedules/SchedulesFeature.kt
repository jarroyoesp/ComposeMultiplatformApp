package com.jarroyo.feature.schedules

import androidx.compose.runtime.Composable
import com.jarroyo.feature.schedules.api.destination.ScheduleListDestination
import com.jarroyo.feature.schedules.api.interactor.GetSchedulesInteractor
import com.jarroyo.feature.schedules.interactor.GetSchedulesInteractorImpl
import com.jarroyo.feature.schedules.ui.list.ScheduleListScreen
import com.jarroyo.feature.schedules.ui.list.ScheduleListViewModel
import com.jarroyo.library.feature.Feature
import com.jarroyo.library.navigation.api.destination.NavigationDestination
import org.koin.core.module.Module
import org.koin.core.module.dsl.factoryOf
import org.koin.dsl.module

class SchedulesFeature : Feature() {
    override val id = "Schedules"

    override val composableDestinations: Map<NavigationDestination, @Composable () -> Unit> = mapOf(
         ScheduleListDestination to { ScheduleListScreen() },
    )

    override val navigationSuiteEntry: NavigationSuiteEntry = NavigationSuiteEntry(
        priority = 0,
        route = ScheduleListDestination.route,
        label = "Schedules",
    )

    companion object {
        val module: Module = module {
            factory<GetSchedulesInteractor> { GetSchedulesInteractorImpl(get()) }

            factoryOf(::ScheduleListViewModel)
        }
    }
}
