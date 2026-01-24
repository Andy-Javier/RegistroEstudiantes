package com.Andy.registroestudiantes.presentation.navigation

import kotlinx.serialization.Serializable

sealed class Screen {
    @Serializable
    data object EstudianteList : Screen()
    @Serializable
    data object EstudianteAdd : Screen()
    @Serializable
    data object AsignaturaList : Screen()
    @Serializable
    data object AsignaturaAdd : Screen()
}