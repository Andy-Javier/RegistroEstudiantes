package com.Andy.registroestudiantes.presentation.asignatura.list

import com.Andy.registroestudiantes.domain.model.Asignatura

data class AsignaturaListState(
    val asignaturas: List<Asignatura> = emptyList()
)

sealed class AsignaturaListEvent {
    data class DeleteAsignatura(val asignatura: Asignatura) : AsignaturaListEvent()
}