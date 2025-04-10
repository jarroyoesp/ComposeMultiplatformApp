package com.jarroyo.feature.home.shared.di

import com.jarroyo.feature.home.api.interactor.AddFavoriteInteractor
import com.jarroyo.feature.home.api.interactor.GetFavoritesInteractor
import com.jarroyo.feature.home.api.interactor.GetLaunchDetailInteractor
import com.jarroyo.feature.home.api.interactor.GetLaunchesInteractor
import com.jarroyo.feature.home.api.interactor.GetSchedulesInteractor
import com.jarroyo.feature.home.api.interactor.OpenUrlInBrowserInteractor
import com.jarroyo.feature.home.api.interactor.RemoveFavoriteInteractor
import com.jarroyo.feature.home.shared.interactor.AddFavoriteInteractorImpl
import com.jarroyo.feature.home.shared.interactor.GetFavoritesInteractorImpl
import com.jarroyo.feature.home.shared.interactor.GetLaunchDetailInteractorImpl
import com.jarroyo.feature.home.shared.interactor.GetLaunchesInteractorImpl
import com.jarroyo.feature.home.shared.interactor.GetSchedulesInteractorImpl
import com.jarroyo.feature.home.shared.interactor.OpenUrlInBrowserInteractorImpl
import com.jarroyo.feature.home.shared.interactor.RemoveFavoriteInteractorImpl
import com.jarroyo.feature.home.shared.ui.HomeViewModel
import com.jarroyo.feature.home.shared.ui.launchdetail.LaunchDetailViewModel
import org.koin.core.module.dsl.factoryOf
import org.koin.dsl.module

val featureHomeModule = module {
    factory<AddFavoriteInteractor> { AddFavoriteInteractorImpl(get()) }
    factory<GetFavoritesInteractor> { GetFavoritesInteractorImpl(get()) }
    factory<GetLaunchesInteractor> { GetLaunchesInteractorImpl(get()) }
    factory<GetLaunchDetailInteractor> { GetLaunchDetailInteractorImpl(get()) }
    factory<GetSchedulesInteractor> { GetSchedulesInteractorImpl(get()) }
    factory<OpenUrlInBrowserInteractor> { OpenUrlInBrowserInteractorImpl() }
    factory<RemoveFavoriteInteractor> { RemoveFavoriteInteractorImpl(get()) }

    factoryOf(::HomeViewModel)
    factoryOf(::LaunchDetailViewModel)
}
