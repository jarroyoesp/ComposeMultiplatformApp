package com.jarroyo.feature.home.shared.di

import com.jarroyo.library.navigation.di.navigationModule
import com.jarroyo.library.network.di.networkModule
import org.koin.core.context.startKoin
import org.koin.dsl.KoinAppDeclaration
import com.jarroyo.feature.home.shared.sqldelight.platformModule

fun initKoin(appDeclaration: KoinAppDeclaration = {}) =
    startKoin {
        appDeclaration()
        modules(
            featureHomeModule,
            navigationModule,
            networkModule,
            platformModule(),
        )
    }
