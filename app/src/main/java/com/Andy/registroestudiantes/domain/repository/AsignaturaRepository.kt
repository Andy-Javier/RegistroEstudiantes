package com.Andy.registroestudiantes.domain.repository

import com.Andy.registroestudiantes.domain.model.Asignatura
import kotlinx.coroutines.flow.Flow

interface AsignaturaRepository {
    suspend fun save(asignatura: Asignatura)
    fun getAll(): Flow<List<Asignatura>>
    suspend fun findByNombre(nombre: String): Asignatura?
    suspend fun delete(asignatura: Asignatura)
}