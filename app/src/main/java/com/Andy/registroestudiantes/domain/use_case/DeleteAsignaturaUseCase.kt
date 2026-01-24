package com.Andy.registroestudiantes.domain.use_case

import com.Andy.registroestudiantes.domain.model.Asignatura
import com.Andy.registroestudiantes.domain.repository.AsignaturaRepository
import javax.inject.Inject

class DeleteAsignaturaUseCase @Inject constructor(
    private val repository: AsignaturaRepository
) {
    suspend operator fun invoke(asignatura: Asignatura) = repository.delete(asignatura)
}