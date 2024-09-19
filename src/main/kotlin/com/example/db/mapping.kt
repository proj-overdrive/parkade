package com.example.db

import com.example.model.Spot
import kotlinx.coroutines.Dispatchers
import org.jetbrains.exposed.dao.EntityClass
import org.jetbrains.exposed.dao.UUIDEntity
import org.jetbrains.exposed.dao.id.EntityID
import org.jetbrains.exposed.dao.id.UUIDTable
import org.jetbrains.exposed.sql.Transaction
import org.jetbrains.exposed.sql.transactions.experimental.newSuspendedTransaction
import java.util.UUID

// Define the SpotTable to match the 'spot' table schema
object SpotTable : UUIDTable("spot") {
    val ownerId = varchar("owner_id", 255) // Updated to match 'owner_id'
    val address = text("address")           // Updated to match 'address'
    val latitude = double("latitude")       // Updated to match 'latitude'
    val longitude = double("longitude")     // Updated to match 'longitude'
}

// Data Access Object for Spot
class SpotDAO(id: EntityID<UUID>) : UUIDEntity(id) {
    companion object : EntityClass<UUID, SpotDAO>(SpotTable)

    var ownerId by SpotTable.ownerId
    var address by SpotTable.address
    var latitude by SpotTable.latitude
    var longitude by SpotTable.longitude
}

// Function to handle transactions
suspend fun <T> suspendTransaction(block: Transaction.() -> T): T =
    newSuspendedTransaction(Dispatchers.IO, statement = block)

// Function to convert DAO to model
fun daoToModel(dao: SpotDAO) = Spot(
    id = dao.id.value.toString(), // Assuming 'id' is an Int and converting to String
    ownerId = dao.ownerId,
    address = dao.address,
    latitude = dao.latitude,
    longitude = dao.longitude
)