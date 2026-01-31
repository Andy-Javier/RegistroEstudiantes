package com.Andy.registroestudiantes.presentation.penalidad.edit

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.toRoute
import com.Andy.registroestudiantes.domain.model.TipoPenalidad
import com.Andy.registroestudiantes.domain.repository.TipoPenalidadRepository
import com.Andy.registroestudiantes.domain.use_case.penalidades.RegistrarTipoPenalidadUseCase
import com.Andy.registroestudiantes.presentation.navigation.Screen
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TipoPenalidadEditViewModel @Inject constructor(
    private val registrarTipoPenalidadUseCase: RegistrarTipoPenalidadUseCase,
    private val repository: TipoPenalidadRepository,
    private val savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val _uiState = MutableStateFlow(TipoPenalidadEditState())
    val uiState: StateFlow<TipoPenalidadEditState> = _uiState.asStateFlow()

    init {
        val tipoId = try {
            savedStateHandle.toRoute<Screen.TipoPenalidad>().tipoId
        } catch (e: Exception) {
            0
        }
        
        if (tipoId > 0) {
            viewModelScope.launch {
                repository.getAll().collect { penalidades ->
                    penalidades.find { it.id == tipoId }?.let { penalidad ->
                        _uiState.update {
                            it.copy(
                                tipoId = penalidad.id,
                                nombre = penalidad.nombre,
                                descripcion = penalidad.descripcion,
                                puntosDescuento = penalidad.puntosDescuento.toString()
                            )
                        }
                    }
                }
            }
        }
    }

    fun onEvent(event: TipoPenalidadEditEvent) {
        when (event) {
            is TipoPenalidadEditEvent.NombreChanged -> {
                _uiState.update { it.copy(nombre = event.nombre, errorMessage = null) }
            }
            is TipoPenalidadEditEvent.DescripcionChanged -> {
                _uiState.update { it.copy(descripcion = event.descripcion, errorMessage = null) }
            }
            is TipoPenalidadEditEvent.PuntosChanged -> {
                _uiState.update { it.copy(puntosDescuento = event.puntos, errorMessage = null) }
            }
            is TipoPenalidadEditEvent.SavePenalidad -> {
                savePenalidad(event.onSuccess)
            }
        }
    }

    private fun savePenalidad(onSuccess: () -> Unit) {
        val currentState = _uiState.value
        val puntosInt = currentState.puntosDescuento.toIntOrNull() ?: 0

        val penalidad = TipoPenalidad(
            id = currentState.tipoId,
            nombre = currentState.nombre,
            descripcion = currentState.descripcion,
            puntosDescuento = puntosInt
        )

        viewModelScope.launch {
            val result = registrarTipoPenalidadUseCase(penalidad)
            result.onSuccess {
                onSuccess()
            }.onFailure { exception ->
                _uiState.update { it.copy(errorMessage = exception.message) }
            }
        }
    }
}