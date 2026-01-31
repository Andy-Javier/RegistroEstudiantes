package com.Andy.registroestudiantes.data.local.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.Andy.registroestudiantes.data.local.dao.AsignaturaDao
import com.Andy.registroestudiantes.data.local.dao.EstudianteDao
import com.Andy.registroestudiantes.data.local.dao.TipoPenalidadDao
import com.Andy.registroestudiantes.data.local.entities.AsignaturaEntity
import com.Andy.registroestudiantes.data.local.entities.EstudianteEntity
import com.Andy.registroestudiantes.data.local.entities.TipoPenalidadEntity

@Database(entities = [EstudianteEntity::class, AsignaturaEntity::class, TipoPenalidadEntity::class], version = 4, exportSchema = false)
abstract class EstudiantesDb : RoomDatabase() {
    abstract fun estudianteDao(): EstudianteDao
    abstract fun asignaturaDao(): AsignaturaDao
    abstract fun tipoPenalidadDao(): TipoPenalidadDao
}