package com.Andy.registroestudiantes.domain.model

data class Estudiante(
    val id: Int? = null,
    val nombres: String,
    val email: String,
    val edad: Int
)