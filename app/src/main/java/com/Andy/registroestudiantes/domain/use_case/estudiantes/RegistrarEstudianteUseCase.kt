package com.Andy.registroestudiantes.domain.use_case.estudiantes

import android.util.Patterns
import com.Andy.registroestudiantes.domain.model.Estudiante
import com.Andy.registroestudiantes.domain.repository.EstudianteRepository
import javax.inject.Inject

class RegistrarEstudianteUseCase @Inject constructor(
    private val repository: EstudianteRepository
) {
    suspend operator fun invoke(estudiante: Estudiante): Result<Unit> {
        val nombreLimpio = estudiante.nombres.trim()
        val emailLimpio = estudiante.email.trim()

        if (nombreLimpio.isBlank() || emailLimpio.isBlank() || estudiante.edad <= 0) {
            return Result.failure(Exception("Todos los campos son obligatorios"))
        }

        if (nombreLimpio.any { it.isDigit() }) {
            return Result.failure(Exception("El nombre no debe contener números"))
        }

        if (!Patterns.EMAIL_ADDRESS.matcher(emailLimpio).matches()) {
            return Result.failure(Exception("El correo electrónico no es válido"))
        }

        if (estudiante.edad < 14 || estudiante.edad > 100) {
            return Result.failure(Exception("La edad debe estar entre 14 y 100 años"))
        }

        val existe = repository.findByNombre(nombreLimpio)
        if (existe != null && existe.id != estudiante.id) {
            return Result.failure(Exception("Ya existe un estudiante con ese nombre"))
        }

        repository.save(estudiante.copy(nombres = nombreLimpio, email = emailLimpio))
        return Result.success(Unit)
    }
}