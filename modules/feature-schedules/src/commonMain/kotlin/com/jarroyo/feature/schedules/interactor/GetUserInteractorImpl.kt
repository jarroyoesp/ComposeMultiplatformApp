package com.jarroyo.feature.schedules.interactor

import com.github.michaelbull.result.Err
import com.github.michaelbull.result.Ok
import com.github.michaelbull.result.Result
import com.jarroyo.feature.schedules.api.interactor.GetUserInteractor
import com.jarroyo.feature.schedules.api.model.User
import dev.gitlive.firebase.firestore.FirebaseFirestore
import dev.gitlive.firebase.firestore.FirebaseFirestoreException

internal class GetUserInteractorImpl(
   private val firestore: FirebaseFirestore,
): GetUserInteractor {
    override suspend operator fun invoke(
        id: String,
    ): Result<User?, Exception> {
        try {
            val query = firestore.collection("Users").where {
                "id" equalTo id
            }

            val users = query.get().documents.map {
                it.data(User.serializer())
            }
            return Ok(users.firstOrNull())
        } catch (e: FirebaseFirestoreException) {
            return Err(e)
        } catch (e: IllegalArgumentException) {
            return Err(e)
        }
    }
}
