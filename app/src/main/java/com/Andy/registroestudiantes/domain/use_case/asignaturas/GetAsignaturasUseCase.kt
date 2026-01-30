package com.Andy.registroestudiantes.domain.use_case.asignaturas

import com.Andy.registroestudiantes.domain.model.Asignatura
import com.Andy.registroestudiantes.domain.repository.AsignaturaRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetAsignaturasUseCase @Inject constructor(
    private val repository: AsignaturaRepository
) {
    operator fun invoke(): Flow<List<Asignatura>> = repository.getAll()
}