package com.jarroyo.feature.home.api.interactor

import com.github.michaelbull.result.Result

interface RemoveFavoriteInteractor {
    suspend operator fun invoke(
        id: String,
    ): Result<Boolean, Exception>
}
