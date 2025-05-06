package com.jarroyo.feature.electricity.interactor

import com.github.michaelbull.result.Err
import com.github.michaelbull.result.Ok
import com.github.michaelbull.result.Result
import com.jarroyo.feature.electricity.api.interactor.GetElectricityDataInteractor
import com.jarroyo.library.network.di.ElectricityApi
import com.jarroyo.library.network.di.ElectricityData
import kotlinx.datetime.LocalDate

internal class GetElectricityDataInteractorImpl(
    private val electricityApi: ElectricityApi,
) : GetElectricityDataInteractor {
    override suspend operator fun invoke(
        startDate: LocalDate,
        endDate: LocalDate,
    ): Result<ElectricityData, Exception> = try {
        Ok(electricityApi.fetchElectricityData(startDate, endDate))
    } catch (e: Exception) {
        Err(e)
    }
}
