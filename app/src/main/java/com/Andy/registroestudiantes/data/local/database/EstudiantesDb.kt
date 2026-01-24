package com.Andy.registroestudiantes.data.local.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.Andy.registroestudiantes.data.local.dao.AsignaturaDao
import com.Andy.registroestudiantes.data.local.dao.EstudianteDao
import com.Andy.registroestudiantes.data.local.entities.AsignaturaEntity
import com.Andy.registroestudiantes.data.local.entities.EstudianteEntity

@Database(entities = [EstudianteEntity::class, AsignaturaEntity::class], version = 3, exportSchema = false)
abstract class EstudiantesDb : RoomDatabase() {
    abstract fun estudianteDao(): EstudianteDao
    abstract fun asignaturaDao(): AsignaturaDao
}