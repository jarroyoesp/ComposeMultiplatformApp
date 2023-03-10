package com.jarroyo.feature.home.di

import com.jarroyo.feature.home.api.interactor.GetRocketsInteractor
import com.jarroyo.feature.home.interactor.GetRocketsInteractorImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
internal interface HomeModule {
    @Binds
    fun bindGetRocketsInteractor(bind: GetRocketsInteractorImpl): GetRocketsInteractor
}
