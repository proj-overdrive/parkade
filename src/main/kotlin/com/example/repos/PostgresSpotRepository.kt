package com.example.repos

import com.example.db.SpotDAO
import com.example.db.daoToModel
import com.example.db.suspendTransaction
import com.example.model.Spot

class PostgresSpotRepository: SpotRepository {
    override suspend fun all(): List<Spot> = suspendTransaction {
        SpotDAO.all().map(::daoToModel)
    }

    override suspend fun create(t: Spot) {
        TODO("Not yet implemented")
    }

    override suspend fun read(id: String): Spot {
        TODO("Not yet implemented")
    }

    override suspend fun update(t: Spot) {
        TODO("Not yet implemented")
    }

    override suspend fun delete(id: String) {
        TODO("Not yet implemented")
    }
}