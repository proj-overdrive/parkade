package com.overdrive.db

import org.jetbrains.exposed.dao.EntityClass
import org.jetbrains.exposed.dao.UUIDEntity
import org.jetbrains.exposed.dao.id.UUIDTable
import java.util.UUID

/**
 * Abstract class for DAO entity classes which includes a function to convert DAO to model.
 *
 * @param E The DAO entity class
 * @param R The model class
 */
abstract class BaseEntityClass<E: UUIDEntity, R>(
    table: UUIDTable,
): EntityClass<UUID, E>(table){
    abstract fun daoToModel(dao: E): R
}
