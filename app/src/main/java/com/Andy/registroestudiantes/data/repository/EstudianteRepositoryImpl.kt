package com.Andy.registroestudiantes.data.repository

import com.Andy.registroestudiantes.data.local.EstudianteDao
import com.Andy.registroestudiantes.data.local.EstudianteEntity
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class EstudianteRepositoryImpl @Inject constructor(
    private val estudianteDao: EstudianteDao
) {
    suspend fun save(estudiante: EstudianteEntity) = estudianteDao.save(estudiante)
    fun getAll(): Flow<List<EstudianteEntity>> = estudianteDao.getAll()
    suspend fun findByNombre(nombre: String) = estudianteDao.findByNombre(nombre)
    suspend fun delete(estudiante: EstudianteEntity) = estudianteDao.delete(estudiante)
}