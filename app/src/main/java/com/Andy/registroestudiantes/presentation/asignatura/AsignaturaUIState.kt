package com.Andy.registroestudiantes.presentation.asignatura

import com.Andy.registroestudiantes.domain.model.Asignatura

data class AsignaturaUIState(
    val asignaturaId: Int? = null,
    val codigo: String = "",
    val nombre: String = "",
    val aula: String = "",
    val creditos: String = "",
    val errorMessage: String? = null,
    val asignaturas: List<Asignatura> = emptyList()
)

sealed class AsignaturaIntent {
    data class CodigoChanged(val codigo: String) : AsignaturaIntent()
    data class NombreChanged(val nombre: String) : AsignaturaIntent()
    data class AulaChanged(val aula: String) : AsignaturaIntent()
    data class CreditosChanged(val creditos: String) : AsignaturaIntent()
    data class OnAsignaturaClick(val asignatura: Asignatura) : AsignaturaIntent()
    data object NuevaAsignatura : AsignaturaIntent()
    data class DeleteAsignatura(val asignatura: Asignatura) : AsignaturaIntent()
    data class SaveAsignatura(val onSuccess: () -> Unit) : AsignaturaIntent()
}