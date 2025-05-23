package com.jarroyo.feature.schedules.interactor

import com.github.michaelbull.result.Err
import com.github.michaelbull.result.Ok
import com.github.michaelbull.result.Result
import com.jarroyo.feature.schedules.api.interactor.RemoveScheduleInteractor
import dev.gitlive.firebase.firestore.FirebaseFirestore
import dev.gitlive.firebase.firestore.FirebaseFirestoreException

internal class RemoveScheduleInteractorImpl(
    private val firestore: FirebaseFirestore,
) : RemoveScheduleInteractor {
    override suspend fun invoke(id: String): Result<Boolean?, Exception> {
        try {
            firestore
                .collection("Schedules")
                .document(id)
                .delete()
            return Ok(true)
        } catch (e: FirebaseFirestoreException) {
            return Err(e)
        }
    }
}
