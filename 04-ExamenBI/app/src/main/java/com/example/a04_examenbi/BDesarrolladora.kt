package com.example.a04_examenbi

import java.util.Date

data class BDesarrolladora (
    var id: Int?,
    var nombre: String?,
    var fechaFundacion: Date?,
    var totalJuegos: Int?,
    var ingresosAnuales: Double?,
    var juegos: MutableList<BJuego> = mutableListOf()
)