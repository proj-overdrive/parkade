package com.overdrive.db

import com.overdrive.model.Booking
import com.overdrive.model.BookingStatus
import org.jetbrains.exposed.dao.UUIDEntity
import org.jetbrains.exposed.dao.id.EntityID
import org.jetbrains.exposed.dao.id.UUIDTable
import org.jetbrains.exposed.sql.javatime.datetime
import java.util.UUID

object BookingTable: UUIDTable("bookings") {
    val parkingSpotId = uuid("parking_spot_id")
    val userId = varchar("user_id", 255)
    val startTime = datetime("start_time")
    val endTime = datetime("end_time")
    val totalPrice = double("total_price")
    val bookingStatus = varchar("booking_status", 10)
    val vehicleLicensePlate = varchar("vehicle_license_plate", 20).nullable()
}

class BookingDAO(id: EntityID<UUID>): UUIDEntity(id) {
    companion object: BaseEntityClass<BookingDAO, Booking>(BookingTable) {
        override fun daoToModel(dao: BookingDAO) = Booking(
            id = dao.id.value.toString(),
            parkingSpotId = dao.parkingSpotId.toString(),
            userId = dao.userId,
            startTime = dao.startTime,
            endTime = dao.endTime,
            totalPrice = dao.totalPrice,
            bookingStatus = BookingStatus.valueOf(dao.bookingStatus),
            vehicleLicensePlate = dao.vehicleLicensePlate
        )
    }

    var parkingSpotId by BookingTable.parkingSpotId
    var userId by BookingTable.userId
    var startTime by BookingTable.startTime
    var endTime by BookingTable.endTime
    var totalPrice by BookingTable.totalPrice
    var bookingStatus by BookingTable.bookingStatus
    var vehicleLicensePlate by BookingTable.vehicleLicensePlate
}
