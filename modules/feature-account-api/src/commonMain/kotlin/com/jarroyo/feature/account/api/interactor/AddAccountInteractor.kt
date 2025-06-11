package com.jarroyo.feature.account.api.interactor

import com.github.michaelbull.result.Result

interface AddAccountInteractor {
    suspend operator fun invoke(
        username: String,
        uuid: String,
    ): Result<Boolean, Exception>
}
