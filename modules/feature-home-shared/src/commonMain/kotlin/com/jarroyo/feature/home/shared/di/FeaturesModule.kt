package com.jarroyo.feature.home.shared.di

import com.jarroyo.feature.account.AccountFeature
import com.jarroyo.feature.common.CommonFeature
import com.jarroyo.feature.electricity.ElectricityFeature
import com.jarroyo.feature.home.shared.ui.HomeFeature
import com.jarroyo.feature.launches.LaunchesFeature
import com.jarroyo.feature.login.LoginFeature
import com.jarroyo.feature.schedules.SchedulesFeature
import com.jarroyo.library.feature.Feature
import org.koin.dsl.module

val featuresModule = module {
    single { AccountFeature() }
    single { CommonFeature() }
    single { ElectricityFeature() }
    single { HomeFeature() }
    single { LaunchesFeature() }
    single { LoginFeature() }
    single { SchedulesFeature() }

    single<List<Feature>> {
        listOf(
            get<AccountFeature>(),
            get<CommonFeature>(),
            get<ElectricityFeature>(),
            get<HomeFeature>(),
            get<LaunchesFeature>(),
            get<LoginFeature>(),
            get<SchedulesFeature>(),
        )
    }
}
