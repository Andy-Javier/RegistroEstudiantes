package com.Andy.registroestudiantes.presentation.asignatura

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.Andy.registroestudiantes.domain.model.Asignatura
import com.Andy.registroestudiantes.domain.use_case.DeleteAsignaturaUseCase
import com.Andy.registroestudiantes.domain.use_case.GetAsignaturasUseCase
import com.Andy.registroestudiantes.domain.use_case.RegistrarAsignaturaUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AsignaturaViewModel @Inject constructor(
    private val getAsignaturasUseCase: GetAsignaturasUseCase,
    private val registrarAsignaturaUseCase: RegistrarAsignaturaUseCase,
    private val deleteAsignaturaUseCase: DeleteAsignaturaUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow(AsignaturaUIState())
    val uiState: StateFlow<AsignaturaUIState> = _uiState.asStateFlow()

    init {
        viewModelScope.launch {
            getAsignaturasUseCase().collect { lista ->
                _uiState.update { it.copy(asignaturas = lista) }
            }
        }
    }

    fun onIntent(intent: AsignaturaIntent) {
        when (intent) {
            is AsignaturaIntent.CodigoChanged -> {
                _uiState.update { it.copy(codigo = intent.codigo, errorMessage = null) }
            }
            is AsignaturaIntent.NombreChanged -> {
                _uiState.update { it.copy(nombre = intent.nombre, errorMessage = null) }
            }
            is AsignaturaIntent.AulaChanged -> {
                _uiState.update { it.copy(aula = intent.aula, errorMessage = null) }
            }
            is AsignaturaIntent.CreditosChanged -> {
                _uiState.update { it.copy(creditos = intent.creditos, errorMessage = null) }
            }
            is AsignaturaIntent.OnAsignaturaClick -> {
                _uiState.update {
                    it.copy(
                        asignaturaId = intent.asignatura.id,
                        codigo = intent.asignatura.codigo,
                        nombre = intent.asignatura.nombre,
                        aula = intent.asignatura.aula,
                        creditos = intent.asignatura.creditos.toString(),
                        errorMessage = null
                    )
                }
            }
            AsignaturaIntent.NuevaAsignatura -> {
                _uiState.update {
                    it.copy(
                        asignaturaId = null,
                        codigo = "",
                        nombre = "",
                        aula = "",
                        creditos = "",
                        errorMessage = null
                    )
                }
            }
            is AsignaturaIntent.DeleteAsignatura -> {
                viewModelScope.launch {
                    deleteAsignaturaUseCase(intent.asignatura)
                }
            }
            is AsignaturaIntent.SaveAsignatura -> {
                saveAsignatura(intent.onSuccess)
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