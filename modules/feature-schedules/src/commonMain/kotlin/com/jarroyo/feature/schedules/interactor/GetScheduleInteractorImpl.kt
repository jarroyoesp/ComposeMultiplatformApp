package com.jarroyo.feature.schedules.interactor

import com.github.michaelbull.result.Err
import com.github.michaelbull.result.Ok
import com.github.michaelbull.result.Result
import com.jarroyo.feature.schedules.api.interactor.GetScheduleInteractor
import com.jarroyo.feature.schedules.api.model.Schedule
import dev.gitlive.firebase.firestore.FirebaseFirestore
import dev.gitlive.firebase.firestore.FirebaseFirestoreException

internal class GetScheduleInteractorImpl(
   private val firestore: FirebaseFirestore,
): GetScheduleInteractor {
    override suspend operator fun invoke(
        id: String,
    ): Result<Schedule?, Exception> {
        try {
            val query = firestore.collection("Schedules").where {
                "id" equalTo id
            }

            val schedules = query.get().documents.map {
                it.data(Schedule.serializer())
            }
            return Ok(schedules.firstOrNull())
        } catch (e: FirebaseFirestoreException) {
            return Err(e)
        } catch (e: IllegalArgumentException) {
            return Err(e)
        }
    }
}
