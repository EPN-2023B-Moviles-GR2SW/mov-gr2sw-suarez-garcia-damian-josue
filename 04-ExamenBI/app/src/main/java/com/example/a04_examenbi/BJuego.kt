package com.example.a04_examenbi

import java.util.Date

data class BJuego (
    var id: Int,
    var nombre: String,
    var fechaLanzamiento: Date,
    var precio: Double,
    var esMultiplayer: Boolean
)