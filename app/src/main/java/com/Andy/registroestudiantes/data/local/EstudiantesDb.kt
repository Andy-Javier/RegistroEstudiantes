package com.Andy.registroestudiantes.data.local

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [EstudianteEntity::class], version = 1, exportSchema = false)
abstract class EstudiantesDb : RoomDatabase() {
    abstract fun estudianteDao(): EstudianteDao
}