package com.overdrive.repos

import com.overdrive.model.Spot

class FakeSpotRepository: CrudRepo<Spot> {
    private val spots = mutableListOf<Spot>(
        Spot("1", "1", "123 Main St", 42.0, 42.0),
        Spot("2", "1", "456 Elm St", 43.0, 43.0),
        Spot("3", "2", "789 Oak St", 44.0, 44.0),
    )

    override suspend fun create(item: Spot): Spot {
        spots.add(item)
        return item
    }

    override suspend fun read(id: String): Spot {
        return spots.find { it.id == id } ?: throw IllegalArgumentException("Spot with id $id not found")
    }

    override suspend fun update(item: Spot): Spot? {
        val index = spots.indexOfFirst { it.id == item.id }
        if (index != -1) {
            spots[index] = item
        } else {
            throw IllegalArgumentException("Spot with id ${item.id} not found")
        }

        return item
    }

    override suspend fun delete(id: String): Boolean {
        val spot = read(id)
        spots.remove(spot)
        return true
    }

    override suspend fun all(): List<Spot> {
        return spots
    }
}