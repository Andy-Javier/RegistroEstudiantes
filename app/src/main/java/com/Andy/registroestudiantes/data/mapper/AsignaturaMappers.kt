package com.Andy.registroestudiantes.data.mapper

import com.Andy.registroestudiantes.data.local.entities.AsignaturaEntity
import com.Andy.registroestudiantes.domain.model.Asignatura

fun AsignaturaEntity.toDomain(): Asignatura {
    return Asignatura(
        id = asignaturaId,
        codigo = codigo,
        nombre = nombre,
        aula = aula,
        creditos = creditos
    )
}

fun Asignatura.toEntity(): AsignaturaEntity {
    return AsignaturaEntity(
        asignaturaId = id,
        codigo = codigo,
        nombre = nombre,
        aula = aula,
        creditos = creditos
    )
}