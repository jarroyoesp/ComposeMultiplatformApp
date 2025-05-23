package com.jarroyo.feature.schedules.interactor

import com.github.michaelbull.result.Err
import com.github.michaelbull.result.Ok
import com.github.michaelbull.result.Result
import com.jarroyo.feature.schedules.api.interactor.GetUserListInteractor
import com.jarroyo.feature.schedules.api.model.User
import dev.gitlive.firebase.firestore.FirebaseFirestore
import dev.gitlive.firebase.firestore.FirebaseFirestoreException

internal class GetUserListInteractorImpl(
   private val firestore: FirebaseFirestore,
): GetUserListInteractor {
    override suspend operator fun invoke(): Result<List<User>?, Exception> {
        try {
            val list =
                firestore.collection("Users").get()
                    .documents.map { document ->
                        document.data(User.serializer())
                    }
            return Ok(list)
        } catch (e: FirebaseFirestoreException) {
            return Err(e)
        }
    }
}
