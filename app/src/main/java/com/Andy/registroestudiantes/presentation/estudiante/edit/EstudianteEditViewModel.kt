package com.Andy.registroestudiantes.presentation.estudiante.edit

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.toRoute
import com.Andy.registroestudiantes.domain.model.Estudiante
import com.Andy.registroestudiantes.domain.repository.EstudianteRepository
import com.Andy.registroestudiantes.domain.use_case.estudiantes.RegistrarEstudianteUseCase
import com.Andy.registroestudiantes.presentation.navigation.Screen
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class EstudianteEditViewModel @Inject constructor(
    private val registrarEstudianteUseCase: RegistrarEstudianteUseCase,
    private val repository: EstudianteRepository,
    private val savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val _uiState = MutableStateFlow(EstudianteEditState())
    val uiState: StateFlow<EstudianteEditState> = _uiState.asStateFlow()

    init {
        val estudianteId = savedStateHandle.toRoute<Screen.Estudiante>().estudianteId
        if (estudianteId > 0) {
            viewModelScope.launch {
                repository.getAll().collect { estudiantes ->
                    estudiantes.find { it.id == estudianteId }?.let { estudiante ->
                        _uiState.update {
                            it.copy(
                                estudianteId = estudiante.id,
                                nombres = estudiante.nombres,
                                email = estudiante.email,
                                edad = estudiante.edad.toString()
                            )
                        }
                    }
                }
            }
        }
    }

    fun onEvent(event: EstudianteEditEvent) {
        when (event) {
            is EstudianteEditEvent.NombreChanged -> {
                _uiState.update { it.copy(nombres = event.nombres, errorMessage = null) }
            }
            is EstudianteEditEvent.EmailChanged -> {
                _uiState.update { it.copy(email = event.email, errorMessage = null) }
            }
            is EstudianteEditEvent.EdadChanged -> {
                _uiState.update { it.copy(edad = event.edad, errorMessage = null) }
            }
            is EstudianteEditEvent.SaveEstudiante -> {
                saveEstudiante(event.onSuccess)
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