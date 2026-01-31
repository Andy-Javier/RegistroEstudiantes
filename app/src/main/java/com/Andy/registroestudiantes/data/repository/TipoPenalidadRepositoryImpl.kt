package com.Andy.registroestudiantes.data.repository

import com.Andy.registroestudiantes.data.local.dao.TipoPenalidadDao
import com.Andy.registroestudiantes.data.mapper.toDomain
import com.Andy.registroestudiantes.data.mapper.toEntity
import com.Andy.registroestudiantes.domain.model.TipoPenalidad
import com.Andy.registroestudiantes.domain.repository.TipoPenalidadRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class TipoPenalidadRepositoryImpl @Inject constructor(
    private val tipoPenalidadDao: TipoPenalidadDao
) : TipoPenalidadRepository {
    override suspend fun save(tipoPenalidad: TipoPenalidad) = tipoPenalidadDao.save(tipoPenalidad.toEntity())
    override fun getAll(): Flow<List<TipoPenalidad>> = tipoPenalidadDao.getAll().map { list -> list.map { it.toDomain() } }
    override suspend fun findByNombre(nombre: String): TipoPenalidad? = tipoPenalidadDao.findByNombre(nombre)?.toDomain()
    override suspend fun delete(tipoPenalidad: TipoPenalidad) = tipoPenalidadDao.delete(tipoPenalidad.toEntity())
}