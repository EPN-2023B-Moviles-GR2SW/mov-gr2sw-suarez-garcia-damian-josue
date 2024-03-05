package com.example.a04_examenbi

import android.widget.EditText

data class BGame(
    var id: Int = 0,
    var nombre: String = "",
    var fechaLanzamiento: String = "",
    var precio: Double = 0.0,
    var esMultiplayer: Boolean = false,
    var idDeveloper: Int = 0
){
    fun checkEsMultiplayer(esMultiplayer: Boolean): String{
        return if(esMultiplayer) "Si" else "No"
    }
    override fun toString(): String {
        return "\nid: $id" +
                "\nnombre: $nombre " +
                "\nfechaLanzamiento: $fechaLanzamiento" +
                "\nprecio $$precio" +
                "\nEs multiplayer: ${checkEsMultiplayer(esMultiplayer)}" +
                "\nidDeveloper: $idDeveloper"
    }
}

