package com.jarroyo.library.navigation.di

import com.jarroyo.library.navigation.api.navigator.AppNavigator
import com.jarroyo.library.navigation.navigator.AppNavigatorImpl
import org.koin.core.component.get
import org.koin.dsl.module

val navigationModule = module {
    single <AppNavigator> { AppNavigatorImpl() }
}
