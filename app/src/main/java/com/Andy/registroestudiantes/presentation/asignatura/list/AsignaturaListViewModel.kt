package com.Andy.registroestudiantes.presentation.asignatura.list

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.Andy.registroestudiantes.domain.use_case.asignaturas.DeleteAsignaturaUseCase
import com.Andy.registroestudiantes.domain.use_case.asignaturas.GetAsignaturasUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AsignaturaListViewModel @Inject constructor(
    private val getAsignaturasUseCase: GetAsignaturasUseCase,
    private val deleteAsignaturaUseCase: DeleteAsignaturaUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow(AsignaturaListState())
    val uiState: StateFlow<AsignaturaListState> = _uiState.asStateFlow()

    init {
        viewModelScope.launch {
            getAsignaturasUseCase().collect { lista ->
                _uiState.update { it.copy(asignaturas = lista) }
            }
        }
    }

    fun onEvent(event: AsignaturaListEvent) {
        when (event) {
            is AsignaturaListEvent.DeleteAsignatura -> {
                viewModelScope.launch {
                    deleteAsignaturaUseCase(event.asignatura)
                }
            }
        }
    }
}