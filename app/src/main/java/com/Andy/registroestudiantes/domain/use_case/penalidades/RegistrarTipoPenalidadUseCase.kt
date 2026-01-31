package com.Andy.registroestudiantes.domain.use_case.penalidades

import com.Andy.registroestudiantes.domain.model.TipoPenalidad
import com.Andy.registroestudiantes.domain.repository.TipoPenalidadRepository
import javax.inject.Inject

class RegistrarTipoPenalidadUseCase @Inject constructor(
    private val repository: TipoPenalidadRepository
) {
    suspend operator fun invoke(tipoPenalidad: TipoPenalidad): Result<Unit> {
        val nombreLimpio = tipoPenalidad.nombre.trim()
        val descripcionLimpia = tipoPenalidad.descripcion.trim()

        if (nombreLimpio.isBlank() || descripcionLimpia.isBlank()) {
            return Result.failure(Exception("Todos los campos son obligatorios"))
        }

        if (tipoPenalidad.puntosDescuento <= 0) {
            return Result.failure(Exception("Los puntos de descuento deben ser un valor mayor a cero"))
        }

        val existe = repository.findByNombre(nombreLimpio)
        if (existe != null && existe.id != tipoPenalidad.id) {
            return Result.failure(Exception("Ya existe un tipo de penalidad registrado con este nombre"))
        }

        repository.save(tipoPenalidad.copy(nombre = nombreLimpio, descripcion = descripcionLimpia))
        return Result.success(Unit)
    }
}