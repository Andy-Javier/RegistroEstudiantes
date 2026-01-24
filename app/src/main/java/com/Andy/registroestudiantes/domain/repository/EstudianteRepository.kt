package com.Andy.registroestudiantes.domain.repository

import com.Andy.registroestudiantes.domain.model.Estudiante
import kotlinx.coroutines.flow.Flow

interface EstudianteRepository {
    suspend fun save(estudiante: Estudiante)
    fun getAll(): Flow<List<Estudiante>>
    suspend fun findByNombre(nombre: String): Estudiante?
    suspend fun delete(estudiante: Estudiante)
}