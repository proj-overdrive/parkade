package com.overdrive.repos

/**
 * Interface for CRUD repositories.
 */
interface CrudRepo<T> {
    /**
     * Retrieve all entities from the database.
     */
    suspend fun all(): List<T>

    /**
     * Create a new entity in the database.
     */
    suspend fun create(t: T): T

    /**
     * Read a single entity from the database.
     */
    suspend fun read(id: String): T?

    /**
     * Update an entity in the database.
     */
    suspend fun update(t: T): T?

    /**
     * Delete an entity from the database.
     */
    suspend fun delete(id: String): Boolean
}
