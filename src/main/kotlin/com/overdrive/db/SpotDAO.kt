package com.overdrive.db

import com.overdrive.model.Spot
import org.jetbrains.exposed.dao.UUIDEntity
import org.jetbrains.exposed.dao.id.EntityID
import org.jetbrains.exposed.dao.id.UUIDTable
import java.util.UUID

/**
 * Table for Spot entities.
 */
object SpotTable : UUIDTable("spot") {
    val ownerId = varchar("owner_id", 255)
    val address = text("address")
    val latitude = double("latitude")
    val longitude = double("longitude")
}

/**
 * DAO for Spot entities.
 */
class SpotDAO(id: EntityID<UUID>) : UUIDEntity(id) {
    companion object : BaseEntityClass<SpotDAO, Spot>(SpotTable) {
        override fun daoToModel(dao: SpotDAO) = Spot(
            id = dao.id.value.toString(),
            ownerId = dao.ownerId,
            address = dao.address,
            latitude = dao.latitude,
            longitude = dao.longitude
        )
    }

    var ownerId by SpotTable.ownerId
    var address by SpotTable.address
    var latitude by SpotTable.latitude
    var longitude by SpotTable.longitude
}
