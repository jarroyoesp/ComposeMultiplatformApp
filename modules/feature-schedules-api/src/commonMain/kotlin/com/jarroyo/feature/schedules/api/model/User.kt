package com.jarroyo.feature.schedules.api.model

import kotlinx.serialization.Serializable

@Serializable
data class User(
    val id: String,
    val lastname: String,
    val name: String,
)
