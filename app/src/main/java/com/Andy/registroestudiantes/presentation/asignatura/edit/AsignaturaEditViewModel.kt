package com.Andy.registroestudiantes.presentation.asignatura.edit

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.toRoute
import com.Andy.registroestudiantes.domain.model.Asignatura
import com.Andy.registroestudiantes.domain.repository.AsignaturaRepository
import com.Andy.registroestudiantes.domain.use_case.asignaturas.RegistrarAsignaturaUseCase
import com.Andy.registroestudiantes.presentation.navigation.Screen
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AsignaturaEditViewModel @Inject constructor(
    private val registrarAsignaturaUseCase: RegistrarAsignaturaUseCase,
    private val repository: AsignaturaRepository,
    private val savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val _uiState = MutableStateFlow(AsignaturaEditState())
    val uiState: StateFlow<AsignaturaEditState> = _uiState.asStateFlow()

    init {
        val asignaturaId = savedStateHandle.toRoute<Screen.Asignatura>().asignaturaId
        if (asignaturaId > 0) {
            viewModelScope.launch {
                repository.getAll().collect { asignaturas ->
                    asignaturas.find { it.id == asignaturaId }?.let { asignatura ->
                        _uiState.update {
                            it.copy(
                                asignaturaId = asignatura.id,
                                codigo = asignatura.codigo,
                                nombre = asignatura.nombre,
                                aula = asignatura.aula,
                                creditos = asignatura.creditos.toString()
                            )
                        }
                    }
                }
            }
        }
    }

    fun onEvent(event: AsignaturaEditEvent) {
        when (event) {
            is AsignaturaEditEvent.CodigoChanged -> {
                _uiState.update { it.copy(codigo = event.codigo, errorMessage = null) }
            }
            is AsignaturaEditEvent.NombreChanged -> {
                _uiState.update { it.copy(nombre = event.nombre, errorMessage = null) }
            }
            is AsignaturaEditEvent.AulaChanged -> {
                _uiState.update { it.copy(aula = event.aula, errorMessage = null) }
            }
            is AsignaturaEditEvent.CreditosChanged -> {
                _uiState.update { it.copy(creditos = event.creditos, errorMessage = null) }
            }
            is AsignaturaEditEvent.SaveAsignatura -> {
                saveAsignatura(event.onSuccess)
            }
        }
    }

    private fun saveAsignatura(onSuccess: () -> Unit) {
        val currentState = _uiState.value
        val creditosInt = currentState.creditos.toIntOrNull() ?: 0

        val asignatura = Asignatura(
            id = currentState.asignaturaId,
            codigo = currentState.codigo,
            nombre = currentState.nombre,
            aula = currentState.aula,
            creditos = creditosInt
        )

        viewModelScope.launch {
            val result = registrarAsignaturaUseCase(asignatura)
            result.onSuccess {
                onSuccess()
            }.onFailure { exception ->
                _uiState.update { it.copy(errorMessage = exception.message) }
            }
        }
    }
}