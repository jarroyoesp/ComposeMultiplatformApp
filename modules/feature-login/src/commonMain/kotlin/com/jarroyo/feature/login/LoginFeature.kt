package com.jarroyo.feature.login

import androidx.compose.runtime.Composable
import com.jarroyo.feature.login.api.destination.LoginDestination
import com.jarroyo.feature.login.api.interactor.LoginInteractor
import com.jarroyo.feature.login.interactor.LoginInteractorImpl
import com.jarroyo.feature.login.ui.login.LoginScreen
import com.jarroyo.feature.login.ui.login.LoginViewModel
import com.jarroyo.library.feature.Feature
import com.jarroyo.library.navigation.api.destination.NavigationDestination
import org.koin.core.module.Module
import org.koin.core.module.dsl.factoryOf
import org.koin.dsl.module

class LoginFeature : Feature() {
    override val id = "Login"

    override val composableDestinations: Map<NavigationDestination, @Composable () -> Unit> = mapOf(
         LoginDestination to { LoginScreen() },
    )

    companion object {
        val module: Module = module {
            factory<LoginInteractor> { LoginInteractorImpl(get()) }

            factoryOf(::LoginViewModel)
        }
    }
}
