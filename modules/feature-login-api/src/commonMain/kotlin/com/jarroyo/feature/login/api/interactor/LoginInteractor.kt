package com.jarroyo.feature.login.api.interactor

import com.github.michaelbull.result.Result

interface LoginInteractor {
    suspend operator fun invoke(
        email: String,
        password: String,
    ): Result<Boolean, Exception>
}
