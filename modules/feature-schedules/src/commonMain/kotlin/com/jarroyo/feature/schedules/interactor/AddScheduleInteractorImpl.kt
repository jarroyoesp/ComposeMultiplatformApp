package com.jarroyo.feature.schedules.interactor

import com.github.michaelbull.result.Err
import com.github.michaelbull.result.Ok
import com.github.michaelbull.result.Result
import com.jarroyo.feature.schedules.api.interactor.AddScheduleInteractor
import com.jarroyo.feature.schedules.api.model.Schedule
import dev.gitlive.firebase.firestore.FirebaseFirestore
import dev.gitlive.firebase.firestore.FirebaseFirestoreException

internal class AddScheduleInteractorImpl(
    private val firestore: FirebaseFirestore,
) : AddScheduleInteractor {
    override suspend fun invoke(schedule: Schedule): Result<Boolean?, Exception> {
        try {
            firestore
                .collection("Schedules")
                .document(schedule.id)
                .set(schedule)
            return Ok(true)
        } catch (e: FirebaseFirestoreException) {
            return Err(e)
        }
    }
}
