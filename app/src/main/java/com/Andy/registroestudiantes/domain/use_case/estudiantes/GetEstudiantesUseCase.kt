package com.Andy.registroestudiantes.domain.use_case.estudiantes

import com.Andy.registroestudiantes.domain.model.Estudiante
import com.Andy.registroestudiantes.domain.repository.EstudianteRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetEstudiantesUseCase @Inject constructor(
    private val repository: EstudianteRepository
) {
    operator fun invoke(): Flow<List<Estudiante>> = repository.getAll()
}