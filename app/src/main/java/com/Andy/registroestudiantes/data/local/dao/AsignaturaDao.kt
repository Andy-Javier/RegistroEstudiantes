package com.Andy.registroestudiantes.data.local.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.Andy.registroestudiantes.data.local.entities.AsignaturaEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface AsignaturaDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun save(asignatura: AsignaturaEntity)

    @Query("SELECT * FROM asignaturas")
    fun getAll(): Flow<List<AsignaturaEntity>>

    @Query("SELECT * FROM asignaturas WHERE LOWER(nombre) = LOWER(:nombre) LIMIT 1")
    suspend fun findByNombre(nombre: String): AsignaturaEntity?

    @Delete
    suspend fun delete(asignatura: AsignaturaEntity)
}