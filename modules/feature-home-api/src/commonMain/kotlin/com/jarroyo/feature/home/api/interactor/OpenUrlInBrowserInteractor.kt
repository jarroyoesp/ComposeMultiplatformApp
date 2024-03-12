package com.jarroyo.feature.home.api.interactor

interface OpenUrlInBrowserInteractor {
    suspend operator fun invoke(url: String)
}
