package com.jarroyo.feature.login.interactor

import com.github.michaelbull.result.Err
import com.github.michaelbull.result.Ok
import com.github.michaelbull.result.Result
import com.jarroyo.feature.account.api.interactor.AddAccountInteractor
import com.jarroyo.feature.login.api.interactor.LoginInteractor
import dev.gitlive.firebase.Firebase
import dev.gitlive.firebase.auth.auth

internal class LoginInteractorImpl(
    private val addAccountInteractor: AddAccountInteractor,
) : LoginInteractor {
    override suspend fun invoke(
        email: String,
        password: String,
    ): Result<Boolean, Exception> {
        try {
            val result = Firebase.auth.signInWithEmailAndPassword(email = email.trim(), password = password.trim())
            addAccountInteractor(username = "", uuid = checkNotNull(result.user?.uid))
            return Ok(true)
        } catch (e: Exception) {
            return Err(e)
        }
    }
}
