package com.jarroyo.feature.schedules.api.interactor

import com.github.michaelbull.result.Result
import com.jarroyo.feature.schedules.api.model.User

interface GetUserInteractor {
    suspend operator fun invoke(
        id: String,
    ): Result<User?, Exception>
}
