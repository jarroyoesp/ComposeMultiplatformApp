package com.jarroyo.feature.schedules

import androidx.compose.runtime.Composable
import com.jarroyo.feature.schedules.api.destination.ScheduleDetailDestination
import com.jarroyo.feature.schedules.api.destination.ScheduleListDestination
import com.jarroyo.feature.schedules.api.destination.UserSelectorDestination
import com.jarroyo.feature.schedules.api.interactor.AddScheduleInteractor
import com.jarroyo.feature.schedules.api.interactor.GetScheduleInteractor
import com.jarroyo.feature.schedules.api.interactor.GetSchedulesInteractor
import com.jarroyo.feature.schedules.api.interactor.GetUserInteractor
import com.jarroyo.feature.schedules.api.interactor.GetUserListInteractor
import com.jarroyo.feature.schedules.api.interactor.RemoveScheduleInteractor
import com.jarroyo.feature.schedules.interactor.AddScheduleInteractorImpl
import com.jarroyo.feature.schedules.interactor.GetScheduleInteractorImpl
import com.jarroyo.feature.schedules.interactor.GetSchedulesInteractorImpl
import com.jarroyo.feature.schedules.interactor.GetUserInteractorImpl
import com.jarroyo.feature.schedules.interactor.GetUserListInteractorImpl
import com.jarroyo.feature.schedules.interactor.RemoveScheduleInteractorImpl
import com.jarroyo.feature.schedules.ui.scheduledetail.ScheduleDetailScreen
import com.jarroyo.feature.schedules.ui.list.ScheduleListScreen
import com.jarroyo.feature.schedules.ui.userselector.UserSelectorViewModel
import com.jarroyo.feature.schedules.ui.scheduledetail.ScheduleDetailViewModel
import com.jarroyo.feature.schedules.ui.list.ScheduleListViewModel
import com.jarroyo.feature.schedules.ui.userselector.UserSelectorScreen
import com.jarroyo.library.feature.Feature
import com.jarroyo.library.navigation.api.destination.NavigationDestination
import org.koin.core.module.Module
import org.koin.core.module.dsl.factoryOf
import org.koin.dsl.module

class SchedulesFeature : Feature() {
    override val id = "Schedules"

    override val composableDestinations: Map<NavigationDestination, @Composable () -> Unit> = mapOf(
        ScheduleDetailDestination to { ScheduleDetailScreen() },
        ScheduleListDestination to { ScheduleListScreen() },
        UserSelectorDestination to { UserSelectorScreen() },
    )

    override val navigationSuiteEntry: NavigationSuiteEntry = NavigationSuiteEntry(
        priority = 0,
        route = ScheduleListDestination.route,
        label = "Schedules",
    )

    companion object {
        val module: Module = module {
            factory<AddScheduleInteractor> { AddScheduleInteractorImpl(get()) }
            factory<GetScheduleInteractor> { GetScheduleInteractorImpl(get()) }
            factory<GetSchedulesInteractor> { GetSchedulesInteractorImpl(get()) }
            factory<GetUserInteractor> { GetUserInteractorImpl(get()) }
            factory<GetUserListInteractor> { GetUserListInteractorImpl(get()) }
            factory<RemoveScheduleInteractor> { RemoveScheduleInteractorImpl(get()) }

            factoryOf(::ScheduleDetailViewModel)
            factoryOf(::ScheduleListViewModel)
            factoryOf(::UserSelectorViewModel)
        }
    }
}
