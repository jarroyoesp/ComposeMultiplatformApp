package com.jarroyo.feature.home.shared.di

import com.jarroyo.feature.home.api.interactor.AddFavoriteInteractor
import com.jarroyo.feature.home.api.interactor.GetFavoritesInteractor
import com.jarroyo.feature.home.api.interactor.GetLaunchDetailInteractor
import com.jarroyo.feature.home.api.interactor.GetLaunchesInteractor
import com.jarroyo.feature.home.api.interactor.OpenUrlInBrowserInteractor
import com.jarroyo.feature.home.api.interactor.RemoveFavoriteInteractor
import com.jarroyo.feature.home.shared.interactor.AddFavoriteInteractorImpl
import com.jarroyo.feature.home.shared.interactor.GetFavoritesInteractorImpl
import com.jarroyo.feature.home.shared.interactor.GetLaunchDetailInteractorImpl
import com.jarroyo.feature.home.shared.interactor.GetLaunchesInteractorImpl
import com.jarroyo.feature.home.shared.interactor.OpenUrlInBrowserInteractorImpl
import com.jarroyo.feature.home.shared.interactor.RemoveFavoriteInteractorImpl
import org.koin.core.component.KoinComponent
import org.koin.core.component.get
import org.koin.dsl.module

val featureHomeModule = module {
    factory<AddFavoriteInteractor> { AddFavoriteInteractorImpl(get()) }
    factory<GetFavoritesInteractor> { GetFavoritesInteractorImpl(get()) }
    factory<GetLaunchesInteractor> { GetLaunchesInteractorImpl(get()) }
    factory<GetLaunchDetailInteractor> { GetLaunchDetailInteractorImpl(get()) }
    factory<OpenUrlInBrowserInteractor> { OpenUrlInBrowserInteractorImpl() }
    factory<RemoveFavoriteInteractor> { RemoveFavoriteInteractorImpl(get()) }
}

class FeatureHomeKoinComponent : KoinComponent {
    val addFavoriteInteractor: AddFavoriteInteractor = get()
    val getFavoritesInteractor: GetFavoritesInteractor = get()
    val getLaunchDetailInteractor: GetLaunchDetailInteractor = get()
    val getLaunchesInteractor: GetLaunchesInteractor = get()
    val openUrlInBrowserInteractor: OpenUrlInBrowserInteractor = get()
    val removeFavoriteInteractor: RemoveFavoriteInteractor = get()
}
