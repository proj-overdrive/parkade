package com.overdrive.plugins

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

fun Application.configureSpotSerialization(spotRepository: CrudRepo<Spot>) {

    install(ContentNegotiation) {
        json()
    }
    routing {
        route("/spots") {
            get {
                val spots = spotRepository.all()
                call.respond(spots)
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
    }
}