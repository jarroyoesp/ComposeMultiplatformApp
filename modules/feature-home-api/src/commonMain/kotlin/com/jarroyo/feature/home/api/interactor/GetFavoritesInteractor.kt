package com.jarroyo.feature.home.api.interactor

import com.github.michaelbull.result.Result

interface GetFavoritesInteractor {
    suspend operator fun invoke(): Result<List<String>, Exception>
}
