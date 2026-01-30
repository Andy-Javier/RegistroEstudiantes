package com.Andy.registroestudiantes.presentation.estudiante.list

import com.Andy.registroestudiantes.domain.model.Estudiante

data class EstudianteListState(
    val estudiantes: List<Estudiante> = emptyList()
)

sealed class EstudianteListEvent {
    data class DeleteEstudiante(val estudiante: Estudiante) : EstudianteListEvent()
}