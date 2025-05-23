package com.jarroyo.feature.schedules.api.interactor

import com.github.michaelbull.result.Result
import com.jarroyo.feature.schedules.api.model.Schedule

interface GetScheduleInteractor {
    suspend operator fun invoke(
        id: String,
    ): Result<Schedule?, Exception>
}
