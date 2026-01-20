package com.Andy.registroestudiantes.presentation

import android.util.Patterns
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.Andy.registroestudiantes.data.local.EstudianteEntity
import com.Andy.registroestudiantes.data.repository.EstudianteRepositoryImpl
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class EstudianteViewModel @Inject constructor(
    private val repository: EstudianteRepositoryImpl
) : ViewModel() {

    private val _uiState = MutableStateFlow(EstudianteUIState())
    val uiState: StateFlow<EstudianteUIState> = _uiState.asStateFlow()

    init {
        viewModelScope.launch {
            repository.getAll().collect { listaEstudiantes ->
                _uiState.update { it.copy(estudiantes = listaEstudiantes) }
            }
        }
    }

    fun onIntent(intent: EstudianteIntent) {
        when (intent) {
            is EstudianteIntent.NombreChanged -> {
                _uiState.update { it.copy(nombres = intent.nombres, errorMessage = null) }
            }
            is EstudianteIntent.EmailChanged -> {
                _uiState.update { it.copy(email = intent.email, errorMessage = null) }
            }
            is EstudianteIntent.EdadChanged -> {
                _uiState.update { it.copy(edad = intent.edad, errorMessage = null) }
            }
            is EstudianteIntent.OnEstudianteClick -> {
                val estudiante = intent.estudiante
                _uiState.update {
                    it.copy(
                        estudianteId = estudiante.estudianteId,
                        nombres = estudiante.nombres,
                        email = estudiante.email,
                        edad = estudiante.edad.toString(),
                        errorMessage = null
                    )
                }
            }
            EstudianteIntent.NuevoEstudiante -> {
                _uiState.update {
                    it.copy(
                        estudianteId = null,
                        nombres = "",
                        email = "",
                        edad = "",
                        errorMessage = null
                    )
                }
            }
            is EstudianteIntent.DeleteEstudiante -> {
                viewModelScope.launch {
                    repository.delete(intent.estudiante)
                }
            }
            is EstudianteIntent.SaveEstudiante -> {
                saveEstudiante(intent.onSuccess)
            }
        }
    }

    private fun saveEstudiante(onSuccess: () -> Unit) {
        val currentState = _uiState.value
        val nombreLimpio = currentState.nombres.trim()
        val emailLimpio = currentState.email.trim()

        if (nombreLimpio.isBlank() || emailLimpio.isBlank() || currentState.edad.isBlank()) {
            _uiState.update { it.copy(errorMessage = "Todos los campos son obligatorios") }
            return
        }

        if (!Patterns.EMAIL_ADDRESS.matcher(emailLimpio).matches()) {
            _uiState.update { it.copy(errorMessage = "El correo electrónico no es válido") }
            return
        }

        val edadInt = currentState.edad.toIntOrNull()
        if (edadInt == null) {
            _uiState.update { it.copy(errorMessage = "La edad debe ser un número") }
            return
        }

        viewModelScope.launch {
            val existe = repository.findByNombre(nombreLimpio)
            
            if (existe != null && existe.estudianteId != currentState.estudianteId) {
                _uiState.update { it.copy(errorMessage = "Ya existe un estudiante con ese nombre") }
                return@launch
            }

            repository.save(
                EstudianteEntity(
                    estudianteId = currentState.estudianteId,
                    nombres = nombreLimpio,
                    email = emailLimpio,
                    edad = edadInt
                )
            )
            onSuccess()
        }
    }
}