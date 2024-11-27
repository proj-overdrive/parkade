package com.overdrive.plugins

import io.ktor.server.application.*
import org.jetbrains.exposed.sql.*
import org.flywaydb.core.Flyway

fun Application.configureDatabases() {
    // Try to get the RDS environment variables, otherwise default to localhost
    val hostname = System.getenv("RDS_HOSTNAME") ?: "http://localhost:8080"
    val port = System.getenv("RDS_PORT") ?: "5432"
    val dbName = System.getenv("RDS_DB_NAME") ?: "postgres"
    val username = System.getenv("RDS_USERNAME") ?: "postgres"
    val password = System.getenv("RDS_PASSWORD") ?: "postgres"

    val jdbcUrl = "jdbc:postgresql://$hostname:$port/$dbName"

    // Apply the Flyway migrations
    val flyway = Flyway.configure()
        .dataSource(
            jdbcUrl,
            username,
            password
        )
        .load()
    flyway.migrate()

    Database.connect(
        url = jdbcUrl,
        driver = "org.postgresql.Driver",
        user = username,
        password = password
    )
}
