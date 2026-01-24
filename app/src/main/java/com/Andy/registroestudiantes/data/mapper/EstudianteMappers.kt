package com.Andy.registroestudiantes.data.mapper

import com.Andy.registroestudiantes.data.local.entities.EstudianteEntity
import com.Andy.registroestudiantes.domain.model.Estudiante

fun EstudianteEntity.toDomain(): Estudiante {
    return Estudiante(
        id = estudianteId,
        nombres = nombres,
        email = email,
        edad = edad
    )
}

fun Estudiante.toEntity(): EstudianteEntity {
    return EstudianteEntity(
        estudianteId = id,
        nombres = nombres,
        email = email,
        edad = edad
    )
}