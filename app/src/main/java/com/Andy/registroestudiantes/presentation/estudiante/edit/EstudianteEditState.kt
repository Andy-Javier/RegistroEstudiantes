package com.Andy.registroestudiantes.presentation.estudiante.edit

data class EstudianteEditState(
    val estudianteId: Int? = null,
    val nombres: String = "",
    val email: String = "",
    val edad: String = "",
    val errorMessage: String? = null
)

sealed class EstudianteEditEvent {
    data class NombreChanged(val nombres: String) : EstudianteEditEvent()
    data class EmailChanged(val email: String) : EstudianteEditEvent()
    data class EdadChanged(val edad: String) : EstudianteEditEvent()
    data class SaveEstudiante(val onSuccess: () -> Unit) : EstudianteEditEvent()
}