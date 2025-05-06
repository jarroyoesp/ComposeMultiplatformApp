package com.jarroyo.feature.launches.api.interactor

import com.github.michaelbull.result.Result

interface GetFavoritesInteractor {
    suspend operator fun invoke(): Result<List<String>, Exception>
}
