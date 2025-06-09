package com.jarroyo.feature.account.interactor

import com.github.michaelbull.result.Err
import com.github.michaelbull.result.Ok
import com.github.michaelbull.result.Result
import com.jarroyo.feature.account.api.interactor.AccountModel
import com.jarroyo.feature.account.api.interactor.GetAccountInteractor
import com.jarroyo.feature.account.sqldelight.DatabaseWrapper
import dev.gitlive.firebase.Firebase
import dev.gitlive.firebase.auth.auth

internal class GetAccountInteractorImpl(
    private val databaseWrapper: DatabaseWrapper,
) : GetAccountInteractor {
    override suspend fun invoke(): Result<AccountModel, Exception> {
        val currentUser = Firebase.auth.currentUser
        return if (currentUser != null) {
            Ok(AccountModel("", currentUser.uid))
        } else {
            Err(Exception("No Accounts"))
        }
    }
}
