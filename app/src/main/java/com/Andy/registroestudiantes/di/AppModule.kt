package com.Andy.registroestudiantes.di

import android.content.Context
import androidx.room.Room
import com.Andy.registroestudiantes.data.local.EstudianteDao
import com.Andy.registroestudiantes.data.local.EstudiantesDb
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {
    @Provides
    @Singleton
    fun provideDatabase(@ApplicationContext context: Context): EstudiantesDb {
        return Room.databaseBuilder(
            context,
            EstudiantesDb::class.java,
            "Estudiantes.db"
        ).build()
    }

    @Provides
    fun provideEstudianteDao(db: EstudiantesDb): EstudianteDao = db.estudianteDao()
}