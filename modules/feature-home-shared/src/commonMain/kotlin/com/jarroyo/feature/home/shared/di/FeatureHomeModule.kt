package com.jarroyo.feature.home.shared.di

import com.jarroyo.feature.home.api.interactor.GetLaunchDetailInteractor
import com.jarroyo.feature.home.api.interactor.GetRocketsInteractor
import com.jarroyo.feature.home.shared.interactor.GetLaunchDetailInteractorImpl
import com.jarroyo.feature.home.shared.interactor.GetRocketsInteractorImpl
import org.koin.core.component.KoinComponent
import org.koin.core.component.get
import org.koin.dsl.module

val featureHomeModule = module {
    factory<GetRocketsInteractor> { GetRocketsInteractorImpl(get()) }
    factory<GetLaunchDetailInteractor> { GetLaunchDetailInteractorImpl(get()) }
}

class FeatureHomeKoinComponent : KoinComponent {
    val getLaunchDetailInteractor: GetLaunchDetailInteractor = get()
    val getRocketsInteractor: GetRocketsInteractor = get()
}
