package com.jarroyo.composeapp.di

import com.jarroyo.feature.home.shared.di.featureHomeModule
import org.koin.core.context.startKoin
import org.koin.dsl.KoinAppDeclaration

fun initKoin(enableNetworkLogs: Boolean = false, appDeclaration: KoinAppDeclaration = {}) =
    startKoin {
        appDeclaration()
        modules(
            featureHomeModule,
        )
    }