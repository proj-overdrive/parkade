package com.overdrive.repos

import com.overdrive.db.BaseEntityClass
import com.overdrive.db.suspendTransaction
import org.jetbrains.exposed.dao.UUIDEntity
import java.util.UUID

/**
 * Abstract class for Postgres repositories which includes common CRUD operations.
 *
 * @param E The DAO entity class
 * @param R The model class
 * @param EC The DAO entity class companion object
 */
abstract class PostgresRepository<E: UUIDEntity, R: Any, EC: BaseEntityClass<E, R>>(
    val dao: EC
) : CrudRepo<R> {

    /**
     * Retrieve all entities from the database.
     *
     * @return A list of all entities
     */
    override suspend fun all(): List<R> = suspendTransaction {
        dao.all().map(dao::daoToModel)
    }

    /**
     * Read a single entity from the database.
     *
     * @param id The ID of the entity to read
     * @return The entity with the given ID, or null if it does not exist
     */
    override suspend fun read(id: String): R? = suspendTransaction {
        dao.findById(UUID.fromString(id))
    }?.let(dao::daoToModel)

    /**
     * Delete an entity from the database.
     *
     * @param id The ID of the entity to delete
     * @return True if the entity was deleted, false otherwise
     */
    override suspend fun delete(id: String): Boolean = suspendTransaction {
        dao.findById(UUID.fromString(id))?.delete() != null
    }
}
