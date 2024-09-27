package com.overdrive.model

import kotlinx.serialization.Serializable

@Serializable
data class Spot(
    val id: String,
    val ownerId: String,
    val address: String,
    val latitude: Double,
    val longitude: Double,
)
