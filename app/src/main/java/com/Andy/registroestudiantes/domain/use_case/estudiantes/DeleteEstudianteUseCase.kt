package com.Andy.registroestudiantes.domain.use_case.estudiantes

import com.Andy.registroestudiantes.domain.model.Estudiante
import com.Andy.registroestudiantes.domain.repository.EstudianteRepository
import javax.inject.Inject

class DeleteEstudianteUseCase @Inject constructor(
    private val repository: EstudianteRepository
) {
    suspend operator fun invoke(estudiante: Estudiante) = repository.delete(estudiante)
}