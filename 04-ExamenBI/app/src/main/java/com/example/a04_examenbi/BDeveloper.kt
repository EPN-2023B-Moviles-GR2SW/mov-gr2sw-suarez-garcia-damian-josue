package com.example.a04_examenbi

data class BDeveloper(
    var id: Int = 0,
    var nombre: String = "",
    var fechaFundacion: String = "",
    var totalJuegos: Int = 0,
    var ingresosAnuales: Double = 0.0,
) {
    //constructor() : this(null, "", "", 0, 0.0)

    override fun toString(): String {
        return "\nid: $id " +
                "\nnombre $nombre" +
                "\nfechaFundacion: $fechaFundacion." +
                "\ntotalJuegos: $totalJuegos" +
                "\ningresosAnuales: $ingresosAnuales"
    }
}
