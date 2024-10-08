package com.overdrive

import com.overdrive.model.Booking
import com.overdrive.model.BookingStatus
import com.overdrive.model.Spot
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.request.delete
import io.ktor.client.request.get
import io.ktor.client.request.post
import io.ktor.client.request.put
import io.ktor.client.request.setBody
import io.ktor.http.ContentType
import io.ktor.http.HttpStatusCode
import io.ktor.http.contentType
import io.ktor.serialization.kotlinx.json.json
import io.ktor.server.testing.testApplication
import java.time.LocalDateTime
import kotlin.test.Test
import kotlin.test.assertContains
import kotlin.test.assertEquals
import kotlin.test.assertFalse

class BookingTest {

    fun createBooking(spotId: String): Booking {
        return Booking("x", spotId, "x", LocalDateTime.now(), LocalDateTime.now(), 0.0, BookingStatus.PENDING, "x")
    }

    suspend fun createBooking(client: HttpClient): Booking {
        val booking = createBooking(getSpots(client).random().id)
        val response = client.post("/bookings") {
            contentType(ContentType.Application.Json)
            setBody(booking)
        }
        assertEquals(HttpStatusCode.OK, response.status)
        return response.body<Booking>()
    }

    suspend fun getBookings(client: HttpClient): List<Booking> {
        val response = client.get("/bookings")
        val results = response.body<List<Booking>>()

        assertEquals(HttpStatusCode.OK, response.status)
        return results
    }

    suspend fun getBookings(client: HttpClient, booking: Booking): List<Booking> {
        val response = client.get("/bookings/${booking.parkingSpotId}")
        assertEquals(HttpStatusCode.OK, response.status)
        return response.body<List<Booking>>()
    }

    suspend fun updateBooking(client: HttpClient, booking: Booking) {
        val response = client.put("/bookings/${booking.id}") {
            contentType(ContentType.Application.Json)
            setBody(booking)
        }
        assertEquals(HttpStatusCode.OK, response.status)
    }

    suspend fun deleteBooking(client: HttpClient, booking: Booking) {
        val response = client.delete("/bookings/${booking.id}") {
            contentType(ContentType.Application.Json)
            setBody(booking)
        }
        assertEquals(HttpStatusCode.NoContent, response.status)
    }

    suspend fun getSpots(client: HttpClient): List<Spot> {
        val response = client.get("/spots")
        val results = response.body<List<Spot>>()

        assertEquals(HttpStatusCode.OK, response.status)
        return results
    }

    @Test
    fun testBooking() = testApplication {
        application { module() }
        val client = createClient {
            install(ContentNegotiation) {
                json()
            }
        }

        val booking = createBooking(client)
        assertContains(getBookings(client), booking)

        val updatedBooking = booking.copy(bookingStatus = BookingStatus.CONFIRMED)
        updateBooking(client, updatedBooking)

        val bookingLookUp = getBookings(client, updatedBooking)
        assertContains(bookingLookUp, updatedBooking)

        deleteBooking(client, updatedBooking)
        assertFalse(getBookings(client).contains(booking) || getBookings(client).contains(updatedBooking))
    }
}