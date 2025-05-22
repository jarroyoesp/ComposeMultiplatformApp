package com.jarroyo.feature.schedules.api.interactor

import com.github.michaelbull.result.Result
import com.jarroyo.feature.schedules.api.model.User

interface GetUserListInteractor {
    suspend operator fun invoke(
    ): Result<List<User>?, Exception>
}
