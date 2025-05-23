package com.jarroyo.feature.schedules.api.model

import kotlinx.serialization.Serializable

@Serializable
data class Schedule(
    val id: String,
    val time: String,
    val slots: Int,
    val users: List<String>,
)
