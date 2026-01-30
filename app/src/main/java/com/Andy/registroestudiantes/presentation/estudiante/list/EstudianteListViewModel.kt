package com.Andy.registroestudiantes.presentation.estudiante.list

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.Andy.registroestudiantes.domain.use_case.estudiantes.DeleteEstudianteUseCase
import com.Andy.registroestudiantes.domain.use_case.estudiantes.GetEstudiantesUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class EstudianteListViewModel @Inject constructor(
    private val getEstudiantesUseCase: GetEstudiantesUseCase,
    private val deleteEstudianteUseCase: DeleteEstudianteUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow(EstudianteListState())
    val uiState: StateFlow<EstudianteListState> = _uiState.asStateFlow()

    init {
        viewModelScope.launch {
            getEstudiantesUseCase().collect { lista ->
                _uiState.update { it.copy(estudiantes = lista) }
            }
        }
    }

    fun onEvent(event: EstudianteListEvent) {
        when (event) {
            is EstudianteListEvent.DeleteEstudiante -> {
                viewModelScope.launch {
                    deleteEstudianteUseCase(event.estudiante)
                }
            }
        }
    }
}