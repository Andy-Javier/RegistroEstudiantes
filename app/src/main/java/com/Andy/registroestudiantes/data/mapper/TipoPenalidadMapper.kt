package com.Andy.registroestudiantes.data.mapper

import com.Andy.registroestudiantes.data.local.entities.TipoPenalidadEntity
import com.Andy.registroestudiantes.domain.model.TipoPenalidad

fun TipoPenalidadEntity.toDomain(): TipoPenalidad {
    return TipoPenalidad(
        id = tipoId,
        nombre = nombre,
        descripcion = descripcion,
        puntosDescuento = puntosDescuento
    )
}

fun TipoPenalidad.toEntity(): TipoPenalidadEntity {
    return TipoPenalidadEntity(
        tipoId = id,
        nombre = nombre,
        descripcion = descripcion,
        puntosDescuento = puntosDescuento
    )
}