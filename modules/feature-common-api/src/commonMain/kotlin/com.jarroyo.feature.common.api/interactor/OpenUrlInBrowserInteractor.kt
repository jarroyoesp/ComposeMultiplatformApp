package com.jarroyo.feature.common.api.interactor

interface OpenUrlInBrowserInteractor {
    suspend operator fun invoke(url: String)
}
