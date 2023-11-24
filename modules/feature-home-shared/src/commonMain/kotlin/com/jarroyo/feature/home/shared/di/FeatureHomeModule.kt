package com.jarroyo.feature.home.shared.di

import com.jarroyo.feature.home.shared.home.HomeViewModel
import org.koin.core.component.KoinComponent
import org.koin.core.component.get
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module

val featureHomeModule = module {
    singleOf(::HomeViewModel)
}

class FeatureHomeKoinComponent : KoinComponent {
    val homeViewModel : HomeViewModel = get()
}