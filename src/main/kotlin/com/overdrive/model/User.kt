package com.overdrive.model

data class User(
    val id: String,
    val name: String,
    val email: String,
    val phoneNumber: String? = null,
    val registeredSince: String,
    val vehicleDetails: List<Vehicle>? = null
)
