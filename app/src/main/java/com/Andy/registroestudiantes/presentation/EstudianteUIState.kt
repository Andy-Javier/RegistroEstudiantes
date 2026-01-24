package com.Andy.registroestudiantes.presentation

import com.Andy.registroestudiantes.domain.model.Estudiante

data class EstudianteUIState(
    val estudianteId: Int? = null,
    val nombres: String = "",
    val email: String = "",
    val edad: String = "",
    val errorMessage: String? = null,
    val estudiantes: List<Estudiante> = emptyList()
)

sealed class EstudianteIntent {
    data class NombreChanged(val nombres: String) : EstudianteIntent()
    data class EmailChanged(val email: String) : EstudianteIntent()
    data class EdadChanged(val edad: String) : EstudianteIntent()
    data class OnEstudianteClick(val estudiante: Estudiante) : EstudianteIntent()
    data object NuevoEstudiante : EstudianteIntent()
    data class DeleteEstudiante(val estudiante: Estudiante) : EstudianteIntent()
    data class SaveEstudiante(val onSuccess: () -> Unit) : EstudianteIntent()
}