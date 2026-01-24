package com.Andy.registroestudiantes.data.local.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.Andy.registroestudiantes.data.local.entities.EstudianteEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface EstudianteDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun save(estudiante: EstudianteEntity)

    @Query("SELECT * FROM estudiantes")
    fun getAll(): Flow<List<EstudianteEntity>>

    @Query("SELECT * FROM estudiantes WHERE LOWER(nombres) = LOWER(:nombre) LIMIT 1")
    suspend fun findByNombre(nombre: String): EstudianteEntity?

    @Delete
    suspend fun delete(estudiante: EstudianteEntity)
}