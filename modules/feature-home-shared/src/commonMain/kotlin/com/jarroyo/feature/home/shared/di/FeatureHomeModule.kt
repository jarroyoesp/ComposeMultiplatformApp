package com.jarroyo.feature.home.shared.di

import com.jarroyo.feature.home.api.interactor.AddFavoriteInteractor
import com.jarroyo.feature.home.api.interactor.GetFavoritesInteractor
import com.jarroyo.feature.home.api.interactor.GetLaunchDetailInteractor
import com.jarroyo.feature.home.api.interactor.GetRocketsInteractor
import com.jarroyo.feature.home.api.interactor.RemoveFavoriteInteractor
import com.jarroyo.feature.home.shared.interactor.AddFavoriteInteractorImpl
import com.jarroyo.feature.home.shared.interactor.GetFavoritesInteractorImpl
import com.jarroyo.feature.home.shared.interactor.GetLaunchDetailInteractorImpl
import com.jarroyo.feature.home.shared.interactor.GetRocketsInteractorImpl
import com.jarroyo.feature.home.shared.interactor.RemoveFavoriteInteractorImpl
import org.koin.core.component.KoinComponent
import org.koin.core.component.get
import org.koin.dsl.module

val featureHomeModule = module {
    factory<AddFavoriteInteractor> { AddFavoriteInteractorImpl(get()) }
    factory<GetFavoritesInteractor> { GetFavoritesInteractorImpl(get()) }
    factory<GetRocketsInteractor> { GetRocketsInteractorImpl(get()) }
    factory<GetLaunchDetailInteractor> { GetLaunchDetailInteractorImpl(get()) }
    factory<RemoveFavoriteInteractor> { RemoveFavoriteInteractorImpl(get()) }
}

class FeatureHomeKoinComponent : KoinComponent {
    val database: DatabaseWrapper = get()

    val addFavoriteInteractor: AddFavoriteInteractor = get()
    val getFavoritesInteractor: GetFavoritesInteractor = get()
    val getLaunchDetailInteractor: GetLaunchDetailInteractor = get()
    val getRocketsInteractor: GetRocketsInteractor = get()
    val removeFavoriteInteractor: RemoveFavoriteInteractor = get()
}
