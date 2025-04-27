package com.jarroyo.feature.home.shared.di

import com.jarroyo.feature.home.shared.ui.HomeFeature
import com.jarroyo.feature.schedules.SchedulesFeature
import com.jarroyo.library.feature.Feature
import org.koin.dsl.module

val featuresModule = module {
    single { SchedulesFeature() }
    single { HomeFeature() }

    single<List<Feature>> {
        listOf(
            get<SchedulesFeature>(),
            get<HomeFeature>(),
        )
    }
}
