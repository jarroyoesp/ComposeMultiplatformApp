package com.jarroyo.feature.launches

import androidx.compose.runtime.Composable
import com.jarroyo.feature.launches.api.interactor.AddFavoriteInteractor
import com.jarroyo.feature.launches.api.interactor.GetLaunchDetailInteractor
import com.jarroyo.feature.launches.api.interactor.RemoveFavoriteInteractor
import com.jarroyo.feature.launches.api.destination.LaunchDestination
import com.jarroyo.feature.launches.api.destination.LaunchListDestination
import com.jarroyo.feature.launches.api.interactor.GetFavoritesInteractor
import com.jarroyo.feature.launches.api.interactor.GetLaunchesInteractor
import com.jarroyo.feature.launches.di.launchesModule
import com.jarroyo.feature.launches.interactor.AddFavoriteInteractorImpl
import com.jarroyo.feature.launches.interactor.GetFavoritesInteractorImpl
import com.jarroyo.feature.launches.interactor.GetLaunchDetailInteractorImpl
import com.jarroyo.feature.launches.interactor.GetLaunchesInteractorImpl
import com.jarroyo.feature.launches.interactor.RemoveFavoriteInteractorImpl
import com.jarroyo.feature.launches.ui.launchdetail.LaunchDetailScreen
import com.jarroyo.feature.launches.ui.launchlist.LaunchListScreen
import com.jarroyo.feature.launches.ui.launchlist.LaunchListViewModel
import com.jarroyo.feature.launches.ui.launchdetail.LaunchDetailViewModel
import com.jarroyo.library.feature.Feature
import com.jarroyo.library.navigation.api.destination.NavigationDestination
import org.koin.core.module.Module
import org.koin.core.module.dsl.factoryOf
import org.koin.dsl.module

class LaunchesFeature : Feature() {
    override val id = "Launches"

    override val composableDestinations: Map<NavigationDestination, @Composable () -> Unit> = mapOf(
         LaunchDestination to { LaunchDetailScreen() },
         LaunchListDestination to { LaunchListScreen() },
    )

    override val navigationSuiteEntry: NavigationSuiteEntry = NavigationSuiteEntry(
        priority = 0,
        route = LaunchListDestination.route,
        label = "Launches",
    )

    companion object {
        val module: List<Module> = module {
            factory<AddFavoriteInteractor> { AddFavoriteInteractorImpl(get()) }
            factory<GetFavoritesInteractor> { GetFavoritesInteractorImpl(get()) }
            factory<GetLaunchesInteractor> { GetLaunchesInteractorImpl(get()) }
            factory<GetLaunchDetailInteractor> { GetLaunchDetailInteractorImpl(get()) }
            factory<RemoveFavoriteInteractor> { RemoveFavoriteInteractorImpl(get()) }

            factoryOf(::LaunchDetailViewModel)
            factoryOf(::LaunchListViewModel)
        } + launchesModule()
    }
}
