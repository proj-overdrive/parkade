package com.overdrive

import com.overdrive.model.Spot
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.plugins.contentnegotiation.*
import io.ktor.client.request.delete
import io.ktor.client.request.get
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.http.ContentType
import io.ktor.http.HttpStatusCode
import io.ktor.http.contentType
import io.ktor.serialization.kotlinx.json.json
import io.ktor.server.testing.testApplication
import kotlin.test.Test
import kotlin.test.assertContains
import kotlin.test.assertEquals
import kotlin.test.assertFalse
import kotlin.test.assertTrue

class SpotTest {

    fun createSpot(): Spot {
        return Spot("x", "Test Spot", "Test Description", 0.0, 0.0)
    }

    suspend fun getSpots(client: HttpClient): List<Spot> {
        val response = client.get("/spots")
        val results = response.body<List<Spot>>()

        assertEquals(HttpStatusCode.OK, response.status)
        return results
    }

    suspend fun createSpot(client: HttpClient): Spot {
        val spot = createSpot()
        val response = client.post("/spots") {
            contentType(ContentType.Application.Json)
            setBody(spot)
        }
        assertEquals(HttpStatusCode.OK, response.status)
        return response.body<Spot>()
    }

    suspend fun deleteSpot(client: HttpClient, spot: Spot) {
        val response = client.delete("/spots/${spot.id}") {
            contentType(ContentType.Application.Json)
            setBody(spot)
        }
        assertEquals(HttpStatusCode.NoContent, response.status)
    }

    @Test
    fun spotsCanBeRetrieved() = testApplication {
        application { module() }
        val client = createClient {
            install(ContentNegotiation) {
                json()
            }
        }

        val results = getSpots(client)
        assertTrue(results.isNotEmpty(), "Expected non-empty list of spots")
    }

    @Test
    fun spotCanBeAdded() = testApplication {
        application { module() }
        val client = createClient {
            install(ContentNegotiation) {
                json()
            }
        }

        val spotsBeforeAdd = getSpots(client)
        val addedSpot = createSpot(client)
        val spotsAfterAdd = getSpots(client)

        assertFalse(spotsBeforeAdd.contains(addedSpot), "Spot should not exist before added")
        assertContains(spotsAfterAdd, addedSpot, "Spot should exist after added")
    }

    @Test
    fun spotsCanBeDeleted() = testApplication {
        application { module() }
        val client = createClient {
            install(ContentNegotiation) {
                json()
            }
        }

        val spotsBeforeAdd = getSpots(client)
        val addedSpot = createSpot(client)
        val spotsAfterAdd = getSpots(client)
        deleteSpot(client, addedSpot)
        val spotsAfterDelete = getSpots(client)

        assertFalse(spotsBeforeAdd.contains(addedSpot), "Spot should not be in the results")
        assertContains(spotsAfterAdd, addedSpot, "Spot should be in the results")
        assertFalse(spotsAfterDelete.contains(addedSpot), "Spot should not be in the results")
    }
}