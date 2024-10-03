package com.overdrive.repos

import com.overdrive.db.SpotDAO
import com.overdrive.db.suspendTransaction
import com.overdrive.model.Spot
import java.util.UUID

/**
 * Repository for Spot entities.
 */
class PostgresSpotRepository: CrudRepo<Spot>, PostgresRepository<SpotDAO, Spot, SpotDAO.Companion>(SpotDAO) {
    override suspend fun create(t: Spot): Spot = suspendTransaction {
        val spotDAO = SpotDAO.new {
            ownerId = t.ownerId
            address = t.address
            longitude = t.longitude
            latitude = t.latitude

        }
        SpotDAO.daoToModel(spotDAO)
    }

    override suspend fun update(t: Spot): Spot? = suspendTransaction {
        val spotDAO = SpotDAO.findById(UUID.fromString(t.id))
        spotDAO?.apply {
            ownerId = t.ownerId
            address = t.address
            longitude = t.longitude
            latitude = t.latitude
        }?.let(SpotDAO::daoToModel)
    }
}
