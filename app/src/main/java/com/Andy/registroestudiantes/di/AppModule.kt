package com.Andy.registroestudiantes.di

import android.content.Context
import androidx.room.Room
import com.Andy.registroestudiantes.data.local.dao.AsignaturaDao
import com.Andy.registroestudiantes.data.local.dao.EstudianteDao
import com.Andy.registroestudiantes.data.local.dao.TipoPenalidadDao
import com.Andy.registroestudiantes.data.local.database.EstudiantesDb
import com.Andy.registroestudiantes.data.repository.AsignaturaRepositoryImpl
import com.Andy.registroestudiantes.data.repository.EstudianteRepositoryImpl
import com.Andy.registroestudiantes.data.repository.TipoPenalidadRepositoryImpl
import com.Andy.registroestudiantes.domain.repository.AsignaturaRepository
import com.Andy.registroestudiantes.domain.repository.EstudianteRepository
import com.Andy.registroestudiantes.domain.repository.TipoPenalidadRepository
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {
    @Binds
    @Singleton
    abstract fun bindEstudianteRepository(
        estudianteRepositoryImpl: EstudianteRepositoryImpl
    ): EstudianteRepository

    @Binds
    @Singleton
    abstract fun bindAsignaturaRepository(
        asignaturaRepositoryImpl: AsignaturaRepositoryImpl
    ): AsignaturaRepository

    @Binds
    @Singleton
    abstract fun bindTipoPenalidadRepository(
        tipoPenalidadRepositoryImpl: TipoPenalidadRepositoryImpl
    ): TipoPenalidadRepository
}

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
        ).fallbackToDestructiveMigration()
            .build()
    }

    @Provides
    fun provideEstudianteDao(db: EstudiantesDb): EstudianteDao = db.estudianteDao()

    @Provides
    fun provideAsignaturaDao(db: EstudiantesDb): AsignaturaDao = db.asignaturaDao()

    @Provides
    fun provideTipoPenalidadDao(db: EstudiantesDb): TipoPenalidadDao = db.tipoPenalidadDao()
}