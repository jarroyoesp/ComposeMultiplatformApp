package com.jarroyo.feature.account.api.interactor

import com.github.michaelbull.result.Result

interface GetAccountInteractor {
    suspend operator fun invoke(): Result<AccountModel, Exception>
}

data class AccountModel(
    val username: String,
    val uuid: String,
)
