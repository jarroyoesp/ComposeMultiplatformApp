package com.jarroyo.library.navigation.di

import com.jarroyo.library.navigation.api.navigator.AppNavigator
import com.jarroyo.library.navigation.navigator.AppNavigatorImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
internal interface NavigationModule {
    @Binds
    fun bindLinkNavigator(bind: AppNavigatorImpl): AppNavigator
}
