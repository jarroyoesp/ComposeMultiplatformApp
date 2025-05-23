package com.jarroyo.feature.schedules.api.interactor

import com.github.michaelbull.result.Result
import com.jarroyo.feature.schedules.api.model.Schedule

interface GetSchedulesInteractor {
    suspend operator fun invoke(
    ): Result<List<Schedule>?, Exception>
}
