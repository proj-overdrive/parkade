package com.example.repos

interface CrudRepo<T> {
    suspend fun all(): List<T>
    suspend fun create(t: T)
    suspend fun read(id: String): T
    suspend fun update(t: T)
    suspend fun delete(id: String)
}
