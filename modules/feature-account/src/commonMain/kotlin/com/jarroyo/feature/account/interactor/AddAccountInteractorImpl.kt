package com.jarroyo.feature.account.interactor

import com.github.michaelbull.result.Ok
import com.github.michaelbull.result.Result
import com.jarroyo.feature.account.Account
import com.jarroyo.feature.account.api.interactor.AddAccountInteractor
import com.jarroyo.feature.account.sqldelight.DatabaseWrapper
import com.jarroyo.feature.account.sqldelight.dao.AccountsDao

internal class AddAccountInteractorImpl(
    private val databaseWrapper: DatabaseWrapper,
) : AddAccountInteractor {
    override suspend fun invoke(username: String, uuid: String): Result<Boolean, Exception> {
        AccountsDao(checkNotNull(databaseWrapper.instance)).insert(
            Account(
                username, uuid,
            ),
        )
        return Ok(true)
    }
}
