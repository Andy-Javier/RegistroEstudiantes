package com.Andy.registroestudiantes.domain.model

data class TipoPenalidad(
    val id: Int? = null,
    val nombre: String,
    val descripcion: String,
    val puntosDescuento: Int
)