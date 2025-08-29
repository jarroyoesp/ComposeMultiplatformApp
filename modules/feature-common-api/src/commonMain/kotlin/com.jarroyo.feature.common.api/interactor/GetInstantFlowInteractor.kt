package com.jarroyo.feature.common.api.interactor

import kotlinx.coroutines.flow.Flow
import kotlin.time.ExperimentalTime
import kotlin.time.Instant

interface GetInstantFlowInteractor {
    @OptIn(ExperimentalTime::class)
    suspend operator fun invoke(): Flow<Instant>
}
