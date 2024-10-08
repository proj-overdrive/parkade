package com.overdrive.repos

import com.overdrive.db.BookingDAO
import com.overdrive.db.suspendTransaction
import com.overdrive.model.Booking
import java.util.UUID

class PostgresBookingRepository: CrudRepo<Booking>, PostgresRepository<BookingDAO, Booking, BookingDAO.Companion>(BookingDAO) {
    override suspend fun create(t: Booking): Booking = suspendTransaction {
        val bookingDAO = BookingDAO.new {
            parkingSpotId = UUID.fromString(t.parkingSpotId)
            userId = t.userId
            startTime = t.startTime
            endTime = t.endTime
            totalPrice = t.totalPrice
            bookingStatus = t.bookingStatus.name
            vehicleLicensePlate = t.vehicleLicensePlate

        }
        BookingDAO.daoToModel(bookingDAO)
    }

    override suspend fun update(t: Booking): Booking? = suspendTransaction {
        val bookingDAO = BookingDAO.findById(UUID.fromString(t.id))
        bookingDAO?.apply {
            parkingSpotId = UUID.fromString(t.parkingSpotId)
            userId = t.userId
            startTime = t.startTime
            endTime = t.endTime
            totalPrice = t.totalPrice
            bookingStatus = t.bookingStatus.name
            vehicleLicensePlate = t.vehicleLicensePlate
        }?.let(BookingDAO::daoToModel)
    }
}
