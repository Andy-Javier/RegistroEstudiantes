package com.Andy.registroestudiantes.data.repository

import com.Andy.registroestudiantes.data.local.dao.EstudianteDao
import com.Andy.registroestudiantes.data.mapper.toDomain
import com.Andy.registroestudiantes.data.mapper.toEntity
import com.Andy.registroestudiantes.domain.model.Estudiante
import com.Andy.registroestudiantes.domain.repository.EstudianteRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class EstudianteRepositoryImpl @Inject constructor(
    private val estudianteDao: EstudianteDao
) : EstudianteRepository {
    override suspend fun save(estudiante: Estudiante) = estudianteDao.save(estudiante.toEntity())
    override fun getAll(): Flow<List<Estudiante>> = estudianteDao.getAll().map { list -> list.map { it.toDomain() } }
    override suspend fun findByNombre(nombre: String): Estudiante? = estudianteDao.findByNombre(nombre)?.toDomain()
    override suspend fun delete(estudiante: Estudiante) = estudianteDao.delete(estudiante.toEntity())
}