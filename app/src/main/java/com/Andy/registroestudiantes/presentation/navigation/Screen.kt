package com.Andy.registroestudiantes.presentation.navigation

import kotlinx.serialization.Serializable

sealed class Screen {
    @Serializable
    data object EstudianteList : Screen()
    
    @Serializable
    data class Estudiante(val estudianteId: Int) : Screen()
    
    @Serializable
    data object AsignaturaList : Screen()
    
    @Serializable
    data class Asignatura(val asignaturaId: Int) : Screen()

    @Serializable
    data object TipoPenalidadList : Screen()

    @Serializable
    data class TipoPenalidad(val tipoId: Int) : Screen()
}