package com.jarroyo.library.network.di

import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import kotlinx.datetime.LocalDate
import kotlinx.serialization.Serializable
import org.koin.core.component.KoinComponent

class ElectricityApi(
    private val client: HttpClient,
    private var baseUrl: String = "https://apidatos.ree.es/es/datos/mercados/precios-mercados-tiempo-real",
) : KoinComponent {
    suspend fun fetchElectricityData(
        startDate: LocalDate,
        endDate: LocalDate,
    ): ElectricityData {
        val url = "$baseUrl?start_date=${startDate}T00:00&end_date=${endDate}T23:59&time_trunc=hour&geo_trunc=electric_system&geo_limit=peninsular&geo_ids=8741"
        return client.get(url).body<ElectricityData>()
    }
}

@Serializable
data class ElectricityData(
    var included: List<Included>,
)

@Serializable
data class Included(
    var type: String,
    var id: String,
    var groupId: String?,
    var attributes: Attributes,
)

@Serializable
data class Attributes(
    var title: String,
    var description: String?,
    var color: String,
    var type: String?,
    var magnitude: String,
    var composite: Boolean,
    var values: List<Values>,
)

@Serializable
data class Values(
    var value: Double,
    var percentage: Int,
    var datetime: String,
)
