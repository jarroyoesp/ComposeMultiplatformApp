package com.jarroyo.feature.electricity.api.interactor

import com.github.michaelbull.result.Result
import com.jarroyo.library.network.di.ElectricityData
import kotlinx.datetime.LocalDate

interface GetElectricityDataInteractor {
    suspend operator fun invoke(
        startDate: LocalDate,
        endDate: LocalDate,
    ): Result<ElectricityData, Exception>
}
