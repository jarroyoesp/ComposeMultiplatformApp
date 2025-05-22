package com.jarroyo.feature.schedules.api.interactor

import com.github.michaelbull.result.Result
import com.jarroyo.feature.schedules.api.model.Schedule

interface AddScheduleInteractor {
    suspend operator fun invoke(
        schedule: Schedule,
    ): Result<Boolean?, Exception>
}
