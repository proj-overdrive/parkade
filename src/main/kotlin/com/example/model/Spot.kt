package com.example.model

import kotlinx.serialization.Serializable

@Serializable
data class Spot(
    override val id: String,
    val ownerId: String,
    val address: String,
    val latitude: Double,
    val longitude: Double,
): Item()
