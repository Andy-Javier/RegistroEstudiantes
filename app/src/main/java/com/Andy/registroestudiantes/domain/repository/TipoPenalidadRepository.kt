package com.Andy.registroestudiantes.domain.repository

import com.Andy.registroestudiantes.domain.model.TipoPenalidad
import kotlinx.coroutines.flow.Flow

interface TipoPenalidadRepository {
    suspend fun save(tipoPenalidad: TipoPenalidad)
    fun getAll(): Flow<List<TipoPenalidad>>
    suspend fun findByNombre(nombre: String): TipoPenalidad?
    suspend fun delete(tipoPenalidad: TipoPenalidad)
}