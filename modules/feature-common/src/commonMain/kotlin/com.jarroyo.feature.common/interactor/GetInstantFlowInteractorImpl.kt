package com.jarroyo.feature.common.interactor

import com.jarroyo.feature.common.api.interactor.GetInstantFlowInteractor
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlin.time.Clock
import kotlin.time.ExperimentalTime
import kotlin.time.Instant

internal class GetInstantFlowInteractorImpl : GetInstantFlowInteractor {
    @OptIn(ExperimentalTime::class)
    override suspend operator fun invoke(): Flow<Instant> = flow {
        while (true) {
            emit(Clock.System.now())
            delay(1000)
        }
    }
}
