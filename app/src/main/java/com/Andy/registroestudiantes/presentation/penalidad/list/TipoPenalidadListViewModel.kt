package com.Andy.registroestudiantes.presentation.penalidad.list

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.Andy.registroestudiantes.domain.use_case.penalidades.DeleteTipoPenalidadUseCase
import com.Andy.registroestudiantes.domain.use_case.penalidades.GetTiposPenalidadesUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TipoPenalidadListViewModel @Inject constructor(
    private val getTiposPenalidadesUseCase: GetTiposPenalidadesUseCase,
    private val deleteTipoPenalidadUseCase: DeleteTipoPenalidadUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow(TipoPenalidadListState())
    val uiState: StateFlow<TipoPenalidadListState> = _uiState.asStateFlow()

    init {
        viewModelScope.launch {
            getTiposPenalidadesUseCase().collect { lista ->
                _uiState.update { it.copy(penalidades = lista) }
            }
        }
    }

    fun onEvent(event: TipoPenalidadListEvent) {
        when (event) {
            is TipoPenalidadListEvent.DeletePenalidad -> {
                viewModelScope.launch {
                    deleteTipoPenalidadUseCase(event.penalidad)
                }
            }
        }
    }
}