package com.jarroyo.feature.home.api.interactor

import com.github.michaelbull.result.Result
import kotlinx.serialization.Serializable

interface GetSchedulesInteractor {
    suspend operator fun invoke(
    ): Result<List<Schedule>?, Exception>
}

@Serializable
data class Schedule(
    val id: String,
    val name: String,
)
