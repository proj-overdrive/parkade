package com.example

import com.example.repos.FakeTaskRepository
import com.example.plugins.*
import com.example.repos.FakeSpotRepository
import com.example.repos.PostgresSpotRepository
import io.ktor.server.application.*

fun main(args: Array<String>) {
    io.ktor.server.netty.EngineMain.main(args)
}

fun Application.module() {
    val repository = FakeTaskRepository()
    val spots = PostgresSpotRepository()

    configureSerialization(repository, spots)
    configureDatabases()
    configureRouting()
}
