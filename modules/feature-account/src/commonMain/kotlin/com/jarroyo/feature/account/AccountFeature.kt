package com.jarroyo.feature.account

import androidx.compose.runtime.Composable
import com.jarroyo.feature.account.api.interactor.AddAccountInteractor
import com.jarroyo.feature.account.api.interactor.GetAccountInteractor
import com.jarroyo.feature.account.di.accountModule
import com.jarroyo.feature.account.interactor.AddAccountInteractorImpl
import com.jarroyo.feature.account.interactor.GetAccountInteractorImpl
import com.jarroyo.library.feature.Feature
import com.jarroyo.library.navigation.api.destination.NavigationDestination
import org.koin.core.module.Module
import org.koin.dsl.module

class AccountFeature : Feature() {
    override val id = "Account"

    override val composableDestinations: Map<NavigationDestination, @Composable () -> Unit> = mapOf(
    )

    companion object {
        val module: List<Module> = module {
            factory<AddAccountInteractor> { AddAccountInteractorImpl(get()) }
            factory<GetAccountInteractor> { GetAccountInteractorImpl(get()) }
        } + accountModule()
    }
}
