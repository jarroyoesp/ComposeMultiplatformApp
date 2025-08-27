package com.jarroyo.feature.common

import com.jarroyo.feature.common.api.interactor.GetInstantFlowInteractor
import com.jarroyo.feature.common.api.interactor.OpenUrlInBrowserInteractor
import com.jarroyo.feature.common.interactor.GetInstantFlowInteractorImpl
import com.jarroyo.feature.common.interactor.OpenUrlInBrowserInteractorImpl
import com.jarroyo.library.feature.Feature
import org.koin.core.module.Module
import org.koin.dsl.module

class CommonFeature : Feature() {
    override val id = "Common"

    companion object {
        val module: Module = module {
            factory<GetInstantFlowInteractor> { GetInstantFlowInteractorImpl() }
            factory<OpenUrlInBrowserInteractor> { OpenUrlInBrowserInteractorImpl() }
        }
    }
}
