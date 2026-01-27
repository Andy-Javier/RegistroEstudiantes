package com.Andy.registroestudiantes.domain.use_case.asignaturas

import com.Andy.registroestudiantes.domain.model.Asignatura
import com.Andy.registroestudiantes.domain.repository.AsignaturaRepository
import javax.inject.Inject

class RegistrarAsignaturaUseCase @Inject constructor(
    private val repository: AsignaturaRepository
) {
    suspend operator fun invoke(asignatura: Asignatura): Result<Unit> {
        val codigoLimpio = asignatura.codigo.trim()
        val nombreLimpio = asignatura.nombre.trim()
        val aulaLimpia = asignatura.aula.trim()

        if (codigoLimpio.isBlank() || nombreLimpio.isBlank() || aulaLimpia.isBlank() || asignatura.creditos <= 0) {
            return Result.failure(Exception("Todos los campos son obligatorios"))
        }

        if (asignatura.creditos < 1 || asignatura.creditos > 6) {
            return Result.failure(Exception("Los créditos deben estar entre 1 y 6"))
        }

        val existe = repository.findByNombre(nombreLimpio)
        if (existe != null && existe.id != asignatura.id) {
            return Result.failure(Exception("Ya existe una asignatura registrada con este nombre"))
        }

        repository.save(asignatura.copy(codigo = codigoLimpio, nombre = nombreLimpio, aula = aulaLimpia))
        return Result.success(Unit)
    }
}