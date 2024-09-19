package com.example

import com.example.model.Spot
import io.ktor.client.call.body
import io.ktor.client.plugins.contentnegotiation.*
import io.ktor.client.request.get
import io.ktor.http.HttpStatusCode
import io.ktor.serialization.kotlinx.json.json
import io.ktor.server.testing.testApplication
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertTrue

class SpotTest {
    @Test
    fun spotsCanBeRetrieved() = testApplication {
        application { module() }

        val client = createClient {
            install(ContentNegotiation) {
                json()
            }
        }

        val response = client.get("/spots")
        val results = response.body<List<Spot>>()

        assertEquals(HttpStatusCode.OK, response.status)
        assertTrue(results.isNotEmpty(), "Expected non-empty list of spots")
    }
}