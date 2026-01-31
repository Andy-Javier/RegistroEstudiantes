package com.Andy.registroestudiantes.domain.use_case.penalidades

import com.Andy.registroestudiantes.domain.model.TipoPenalidad
import com.Andy.registroestudiantes.domain.repository.TipoPenalidadRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetTiposPenalidadesUseCase @Inject constructor(
    private val repository: TipoPenalidadRepository
) {
    operator fun invoke(): Flow<List<TipoPenalidad>> = repository.getAll()
}