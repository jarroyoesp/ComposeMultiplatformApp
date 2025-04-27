package com.jarroyo.feature.schedules.interactor

import com.github.michaelbull.result.Err
import com.github.michaelbull.result.Ok
import com.github.michaelbull.result.Result
import com.jarroyo.feature.schedules.api.interactor.GetSchedulesInteractor
import com.jarroyo.feature.schedules.api.interactor.Schedule
import dev.gitlive.firebase.firestore.FirebaseFirestore
import dev.gitlive.firebase.firestore.FirebaseFirestoreException

internal class GetSchedulesInteractorImpl(
   private val firestore: FirebaseFirestore,
): GetSchedulesInteractor {
    override suspend operator fun invoke(): Result<List<Schedule>?, Exception> {
        try {
            val scheduleList =
                firestore.collection("Schedules").get()
                    .documents.map { document ->
                        document.data(Schedule.serializer())
                    }
            return Ok(scheduleList)
        } catch (e: FirebaseFirestoreException) {
            return Err(e)
        }
    }
}
