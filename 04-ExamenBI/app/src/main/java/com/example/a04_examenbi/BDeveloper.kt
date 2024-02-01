package com.example.a04_examenbi


import java.util.Date

data class BDeveloper(
    var id: Int?,
    var nombre: String,
    var fechaFundacion: String,
    var totalJuegos: Int,
    var ingresosAnuales: Double,
){

override fun toString(): String {
    return "\nid: $id " +
            "\nnombre $nombre" +
            "\nfechaFundacion: $fechaFundacion." +
            "\ntotalJuegos: $totalJuegos" +
            "\ningresosAnuales: $ingresosAnuales"
    }
}