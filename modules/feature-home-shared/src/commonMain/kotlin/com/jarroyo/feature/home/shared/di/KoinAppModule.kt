package com.jarroyo.feature.home.shared.di

import com.jarroyo.library.navigation.di.navigationModule
import com.jarroyo.library.network.di.networkModule
import org.koin.core.context.startKoin
import org.koin.core.module.Module

fun initKoin(additionalModules: List<Module> = emptyList()) =
    startKoin {
        modules(
            additionalModules +
                    featureHomeModule +
                    navigationModule +
                    networkModule +
                    platformModule(),
        )
    }
