package com.example.repos

import com.example.model.Item

abstract class AbstractRepo<T: Item>: CrudRepo<T> {
    override suspend fun read(id: String): T {
        return all().find { it.id == id } ?: throw IllegalArgumentException("No such element with id $id")
    }
}