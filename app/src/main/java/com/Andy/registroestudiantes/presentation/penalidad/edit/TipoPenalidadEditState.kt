package com.Andy.registroestudiantes.presentation.penalidad.edit

data class TipoPenalidadEditState(
    val tipoId: Int? = null,
    val nombre: String = "",
    val descripcion: String = "",
    val puntosDescuento: String = "",
    val errorMessage: String? = null
)

sealed class TipoPenalidadEditEvent {
    data class NombreChanged(val nombre: String) : TipoPenalidadEditEvent()
    data class DescripcionChanged(val descripcion: String) : TipoPenalidadEditEvent()
    data class PuntosChanged(val puntos: String) : TipoPenalidadEditEvent()
    data class SavePenalidad(val onSuccess: () -> Unit) : TipoPenalidadEditEvent()
}