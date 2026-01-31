package com.Andy.registroestudiantes.domain.use_case.penalidades

import com.Andy.registroestudiantes.domain.model.TipoPenalidad
import com.Andy.registroestudiantes.domain.repository.TipoPenalidadRepository
import javax.inject.Inject

class DeleteTipoPenalidadUseCase @Inject constructor(
    private val repository: TipoPenalidadRepository
) {
    suspend operator fun invoke(tipoPenalidad: TipoPenalidad) = repository.delete(tipoPenalidad)
}