package com.Andy.registroestudiantes.data.local

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface EstudianteDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun save(estudiante: EstudianteEntity)

    @Query("SELECT * FROM estudiantes")
    fun getAll(): Flow<List<EstudianteEntity>>

    @Query("SELECT * FROM estudiantes WHERE nombres = :nombre LIMIT 1")
    suspend fun findByNombre(nombre: String): EstudianteEntity?

    @Delete
    suspend fun delete(estudiante: EstudianteEntity)
}