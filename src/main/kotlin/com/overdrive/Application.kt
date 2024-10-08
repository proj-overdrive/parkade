package com.overdrive

import com.overdrive.plugins.*
import com.overdrive.repos.PostgresBookingRepository
import com.overdrive.repos.PostgresSpotRepository
import io.ktor.server.application.*

fun main(args: Array<String>) {
    io.ktor.server.netty.EngineMain.main(args)
}

fun Application.module() {
    val spotRepository = PostgresSpotRepository()
    val bookingRepository = PostgresBookingRepository()

    configureSpotSerialization(spotRepository, bookingRepository)
    configureDatabases()
    configureRouting()
}
