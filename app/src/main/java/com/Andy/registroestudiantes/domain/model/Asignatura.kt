package com.Andy.registroestudiantes.domain.model

data class Asignatura(
    val id: Int? = null,
    val codigo: String,
    val nombre: String,
    val aula: String,
    val creditos: Int
)