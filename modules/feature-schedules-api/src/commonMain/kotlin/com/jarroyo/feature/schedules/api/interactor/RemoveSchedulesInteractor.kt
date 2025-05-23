package com.jarroyo.feature.schedules.api.interactor

import com.github.michaelbull.result.Result

interface RemoveScheduleInteractor {
    suspend operator fun invoke(
        id: String,
    ): Result<Boolean?, Exception>
}
