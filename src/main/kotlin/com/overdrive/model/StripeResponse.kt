package com.overdrive.model

import kotlinx.serialization.Serializable

@Serializable
data class StripeResponse(
    val paymentIntent: String,
    val ephemeralKey: String,
    val customer: String,
    val publishableKey: String
)
