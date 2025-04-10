package com.jarroyo.feature.home.api.interactor

import com.github.michaelbull.result.Result

interface GetSchedulesInteractor {
    suspend operator fun invoke(
    ): Result<List<String>?, Exception>
}

class Schedule(
    id: String,
    name: String,
)