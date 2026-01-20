package com.Andy.registroestudiantes.presentation

import android.util.Patterns
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.Andy.registroestudiantes.data.local.EstudianteEntity
import com.Andy.registroestudiantes.data.repository.EstudianteRepositoryImpl
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class EstudianteViewModel @Inject constructor(
    private val repository: EstudianteRepositoryImpl
) : ViewModel() {
    var estudianteId by mutableStateOf<Int?>(null)
    var nombres by mutableStateOf("")
    var email by mutableStateOf("")
    var edad by mutableStateOf("")
    var errorMessage by mutableStateOf<String?>(null)

    val estudiantes = repository.getAll().stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    fun onEstudianteClick(estudiante: EstudianteEntity) {
        estudianteId = estudiante.estudianteId
        nombres = estudiante.nombres
        email = estudiante.email
        edad = estudiante.edad.toString()
        errorMessage = null
    }

    fun nuevoEstudiante() {
        estudianteId = null
        nombres = ""
        email = ""
        edad = ""
        errorMessage = null
    }

    fun deleteEstudiante(estudiante: EstudianteEntity) {
        viewModelScope.launch {
            repository.delete(estudiante)
        }
    }

    fun saveEstudiante(onSuccess: () -> Unit) {
        val nombreLimpio = nombres.trim()
        val emailLimpio = email.trim()

        if (nombreLimpio.isBlank() || emailLimpio.isBlank() || edad.isBlank()) {
            errorMessage = "Todos los campos son obligatorios"
            return
        }

        if (!Patterns.EMAIL_ADDRESS.matcher(emailLimpio).matches()) {
            errorMessage = "El correo electrónico no es válido"
            return
        }

        val edadInt = edad.toIntOrNull()
        if (edadInt == null) {
            errorMessage = "La edad debe ser un número"
            return
        }

        viewModelScope.launch {
            val existe = repository.findByNombre(nombreLimpio)
            
            // Si existe un estudiante con ese nombre (ya limpio) y no es el que estamos editando
            if (existe != null && existe.estudianteId != estudianteId) {
                errorMessage = "Ya existe un estudiante con ese nombre"
                return@launch
            }

            repository.save(
                EstudianteEntity(
                    estudianteId = estudianteId,
                    nombres = nombreLimpio,
                    email = emailLimpio,
                    edad = edadInt
                )
            )
            onSuccess()
        }
    }
}