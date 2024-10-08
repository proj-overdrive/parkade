package com.overdrive.plugins

import com.overdrive.model.Booking
import com.overdrive.model.Spot
import com.overdrive.repos.CrudRepo
import io.ktor.http.*
import io.ktor.serialization.*
import io.ktor.serialization.kotlinx.json.*
import io.ktor.server.application.*
import io.ktor.server.plugins.contentnegotiation.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

fun Application.configureSpotSerialization(spotRepository: CrudRepo<Spot>, bookingRepository: CrudRepo<Booking>) {

    install(ContentNegotiation) {
        json()
    }
    routing {
        route("/spots") {
            get {
                val spots = spotRepository.all()
                call.respond(spots)
            }

            get("/{spotId}") {
                val spotId = call.parameters["spotId"]
                if (spotId == null) {
                    call.respond(HttpStatusCode.BadRequest)
                    return@get
                }
                val spot = spotRepository.read(spotId)
                if (spot != null) {
                    call.respond(spot)
                } else {
                    call.respond(HttpStatusCode.NotFound)
                }
            }

            post {
                try {
                    val spot = call.receive<Spot>()
                    val created = spotRepository.create(spot)
                    call.respond(created)
                } catch (_: IllegalStateException) {
                    call.respond(HttpStatusCode.BadRequest)
                } catch (_: JsonConvertException) {
                    call.respond(HttpStatusCode.BadRequest)
                }
            }

            delete("/{spotId}") {
                val id = call.parameters["spotId"]

                if (id == null) {
                    call.respond(HttpStatusCode.BadRequest)
                    return@delete
                }
                if (spotRepository.delete(id)) {
                    call.respond(HttpStatusCode.NoContent)
                } else {
                    call.respond(HttpStatusCode.NotFound)
                }
            }
        }

        route("/bookings") {
            get {
                val bookings = bookingRepository.all()
                call.respond(bookings)
            }

            get("/{spotId}") {
                val spotId = call.parameters["spotId"]
                if (spotId == null) {
                    call.respond(HttpStatusCode.BadRequest)
                    return@get
                }
                val bookings = bookingRepository.all().filter { it.parkingSpotId == spotId }
                call.respond(bookings)
            }

            post {
                try {
                    val booking = call.receive<Booking>()
                    val created = bookingRepository.create(booking)
                    call.respond(created)
                } catch (_: IllegalStateException) {
                    call.respond(HttpStatusCode.BadRequest)
                } catch (_: JsonConvertException) {
                    call.respond(HttpStatusCode.BadRequest)
                }
            }

            put("/{bookingId}") {
                val id = call.parameters["bookingId"]
                if (id == null) {
                    call.respond(HttpStatusCode.BadRequest)
                    return@put
                }
                try {
                    val booking = call.receive<Booking>()
                    val updated = bookingRepository.update(booking)
                    if (updated != null) {
                        call.respond(updated)
                    } else {
                        call.respond(HttpStatusCode.NotFound)
                    }
                } catch (_: IllegalStateException) {
                    call.respond(HttpStatusCode.BadRequest)
                } catch (_: JsonConvertException) {
                    call.respond(HttpStatusCode.BadRequest)
                }
            }

            delete("/{bookingId}") {
                val id = call.parameters["bookingId"]

                if (id == null) {
                    call.respond(HttpStatusCode.BadRequest)
                    return@delete
                }
                if (bookingRepository.delete(id)) {
                    call.respond(HttpStatusCode.NoContent)
                } else {
                    call.respond(HttpStatusCode.NotFound)
                }
            }
        }
    }
}