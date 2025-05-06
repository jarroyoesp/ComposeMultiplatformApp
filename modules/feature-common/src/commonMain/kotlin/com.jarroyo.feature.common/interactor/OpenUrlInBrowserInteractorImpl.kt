package com.jarroyo.feature.common.interactor

import com.jarroyo.feature.common.api.interactor.OpenUrlInBrowserInteractor
import org.koin.core.component.KoinComponent

internal class OpenUrlInBrowserInteractorImpl : OpenUrlInBrowserInteractor, KoinComponent {
    override suspend operator fun invoke(url: String) {
        openUrl(url)
    }
}

expect fun openUrl(url: String)
