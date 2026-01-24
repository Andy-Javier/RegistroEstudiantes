package com.Andy.registroestudiantes.data.repository

import com.Andy.registroestudiantes.data.local.dao.AsignaturaDao
import com.Andy.registroestudiantes.data.mapper.toDomain
import com.Andy.registroestudiantes.data.mapper.toEntity
import com.Andy.registroestudiantes.domain.model.Asignatura
import com.Andy.registroestudiantes.domain.repository.AsignaturaRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class AsignaturaRepositoryImpl @Inject constructor(
    private val asignaturaDao: AsignaturaDao
) : AsignaturaRepository {
    override suspend fun save(asignatura: Asignatura) = asignaturaDao.save(asignatura.toEntity())
    override fun getAll(): Flow<List<Asignatura>> = asignaturaDao.getAll().map { list -> list.map { it.toDomain() } }
    override suspend fun findByNombre(nombre: String): Asignatura? = asignaturaDao.findByNombre(nombre)?.toDomain()
    override suspend fun delete(asignatura: Asignatura) = asignaturaDao.delete(asignatura.toEntity())
}