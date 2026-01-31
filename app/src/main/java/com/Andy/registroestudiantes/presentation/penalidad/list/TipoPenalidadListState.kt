package com.Andy.registroestudiantes.presentation.penalidad.list

import com.Andy.registroestudiantes.domain.model.TipoPenalidad

data class TipoPenalidadListState(
    val penalidades: List<TipoPenalidad> = emptyList()
)

sealed class TipoPenalidadListEvent {
    data class DeletePenalidad(val penalidad: TipoPenalidad) : TipoPenalidadListEvent()
}