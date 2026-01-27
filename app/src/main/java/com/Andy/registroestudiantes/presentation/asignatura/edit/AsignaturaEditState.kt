package com.Andy.registroestudiantes.presentation.asignatura.edit

data class AsignaturaEditState(
    val asignaturaId: Int? = null,
    val codigo: String = "",
    val nombre: String = "",
    val aula: String = "",
    val creditos: String = "",
    val errorMessage: String? = null,
    val success: Boolean = false
)

sealed class AsignaturaEditEvent {
    data class CodigoChanged(val codigo: String) : AsignaturaEditEvent()
    data class NombreChanged(val nombre: String) : AsignaturaEditEvent()
    data class AulaChanged(val aula: String) : AsignaturaEditEvent()
    data class CreditosChanged(val creditos: String) : AsignaturaEditEvent()
    data class SaveAsignatura(val onSuccess: () -> Unit) : AsignaturaEditEvent()
}