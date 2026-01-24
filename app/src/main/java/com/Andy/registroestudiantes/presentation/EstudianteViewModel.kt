package com.Andy.registroestudiantes.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.Andy.registroestudiantes.domain.model.Estudiante
import com.Andy.registroestudiantes.domain.use_case.DeleteEstudianteUseCase
import com.Andy.registroestudiantes.domain.use_case.GetEstudiantesUseCase
import com.Andy.registroestudiantes.domain.use_case.RegistrarEstudianteUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class EstudianteViewModel @Inject constructor(
    private val getEstudiantesUseCase: GetEstudiantesUseCase,
    private val registrarEstudianteUseCase: RegistrarEstudianteUseCase,
    private val deleteEstudianteUseCase: DeleteEstudianteUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow(EstudianteUIState())
    val uiState: StateFlow<EstudianteUIState> = _uiState.asStateFlow()

    init {
        viewModelScope.launch {
            getEstudiantesUseCase().collect { listaEstudiantes ->
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
                        estudianteId = estudiante.id,
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
                    deleteEstudianteUseCase(intent.estudiante)
                }
            }
            is EstudianteIntent.SaveEstudiante -> {
                saveEstudiante(intent.onSuccess)
            }
        }
    }

    private fun saveEstudiante(onSuccess: () -> Unit) {
        val currentState = _uiState.value
        val edadInt = currentState.edad.toIntOrNull() ?: 0

        val estudiante = Estudiante(
            id = currentState.estudianteId,
            nombres = currentState.nombres,
            email = currentState.email,
            edad = edadInt
        )

        viewModelScope.launch {
            val result = registrarEstudianteUseCase(estudiante)
            result.onSuccess {
                onSuccess()
            }.onFailure { exception ->
                _uiState.update { it.copy(errorMessage = exception.message) }
            }
        }
    }
}