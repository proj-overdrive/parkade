package com.overdrive.plugins

import com.overdrive.model.StripeResponse
import com.stripe.Stripe
import com.stripe.model.Customer
import com.stripe.model.EphemeralKey
import com.stripe.model.PaymentIntent
import com.stripe.param.CustomerCreateParams
import com.stripe.param.EphemeralKeyCreateParams
import com.stripe.param.PaymentIntentCreateParams

class StripePay {
    val secretApiKey = "sk_test_51QLd7JHSPwdfxfQKwjZDGJV98J6K45ljMUqFR8TsP00KYxsYBPKaUzKdiCbanPKnLYgWhOrjwQSfiwZSWqJjIfCX00eGfYH0Ny"
    val publishableApiKey = "pk_test_51QLd7JHSPwdfxfQKhJ3Xy8f91p7Y89ApSN2I7ZTgPM71TGQzogmNmbiQExBW9RSl5MtDf49Cr4aN6tWeQvm20cWD00ZxsJFaBD"

    val currency = "cad"
    val stripeVersion = "2024-10-28.acacia"

    fun pay(amount: Long): StripeResponse {
        Stripe.apiKey = secretApiKey

        val customerParams = CustomerCreateParams.builder().build()
        val customer = Customer.create(customerParams)
        val ephemeralKey = EphemeralKeyCreateParams.builder()
            .setStripeVersion(stripeVersion)
            .setCustomer(customer.id)
            .build()

        val key = EphemeralKey.create(ephemeralKey)
        val paymentIntentParams = PaymentIntentCreateParams.builder()
            .setAmount(amount)
            .setCurrency(currency)
            .setCustomer(customer.id)
            .setAutomaticPaymentMethods(
                PaymentIntentCreateParams.AutomaticPaymentMethods.builder()
                    .setEnabled(true)
                    .build()
            )
            .build()

        val intent = PaymentIntent.create(paymentIntentParams)

        return StripeResponse(
            intent.clientSecret,
            key.secret,
            customer.id,
            publishableApiKey
        )
    }
}
