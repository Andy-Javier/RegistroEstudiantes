package com.Andy.registroestudiantes.data.local.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.Andy.registroestudiantes.data.local.entities.TipoPenalidadEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface TipoPenalidadDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun save(tipoPenalidad: TipoPenalidadEntity)

    @Query("SELECT * FROM TiposPenalidades")
    fun getAll(): Flow<List<TipoPenalidadEntity>>

    @Query("SELECT * FROM TiposPenalidades WHERE LOWER(nombre) = LOWER(:nombre) LIMIT 1")
    suspend fun findByNombre(nombre: String): TipoPenalidadEntity?

    @Delete
    suspend fun delete(tipoPenalidad: TipoPenalidadEntity)
}