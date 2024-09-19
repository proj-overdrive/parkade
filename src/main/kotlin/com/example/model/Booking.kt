package com.example.model

import kotlinx.serialization.Serializable

enum class BookingStatus {
    PENDING, CONFIRMED, CANCELLED, COMPLETED
}

@Serializable
data class Booking(
    override val id: String,
    val parkingSpotId: String,
    val userId: String,
    val startTime: String,
    val endTime: String,
    val totalPrice: Double,
    val bookingStatus: BookingStatus,
    val vehicleLicensePlate: String? = null
): Item()
