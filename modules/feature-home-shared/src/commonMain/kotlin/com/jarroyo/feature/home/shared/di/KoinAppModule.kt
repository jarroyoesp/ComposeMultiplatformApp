package com.jarroyo.feature.home.shared.di

import com.jarroyo.feature.common.CommonFeature
import com.jarroyo.feature.launches.LaunchesFeature
import com.jarroyo.feature.launches.di.launchesModule
import com.jarroyo.feature.schedules.SchedulesFeature
import com.jarroyo.library.navigation.di.navigationModule
import com.jarroyo.library.network.di.networkModule
import org.koin.core.context.startKoin
import org.koin.core.module.Module

fun initKoin(additionalModules: List<Module> = emptyList()) =
    startKoin {
        modules(
            additionalModules +
                    navigationModule +
                    networkModule +
                    featuresModule +
                    CommonFeature.module +
                    LaunchesFeature.module +
                    SchedulesFeature.module +
                    launchesModule(),
        )
    }
