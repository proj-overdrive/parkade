package com.example.repos

import com.example.model.Spot

class FakeSpotRepository: SpotRepository {
    private val spots = mutableListOf<Spot>(
        Spot("1", "1", "123 Main St", 42.0, 42.0),
        Spot("2", "1", "456 Elm St", 43.0, 43.0),
        Spot("3", "2", "789 Oak St", 44.0, 44.0),
    )

    override suspend fun create(item: Spot) {
        spots.add(item)
    }

    override suspend fun read(id: String): Spot {
        return spots.find { it.id == id } ?: throw IllegalArgumentException("Spot with id $id not found")
    }

    override suspend fun update(item: Spot) {
        val index = spots.indexOfFirst { it.id == item.id }
        if (index != -1) {
            spots[index] = item
        } else {
            throw IllegalArgumentException("Spot with id ${item.id} not found")
        }
    }

    override suspend fun delete(id: String) {
        val spot = read(id)
        spots.remove(spot)
    }

    override suspend fun all(): List<Spot> {
        return spots
    }
}