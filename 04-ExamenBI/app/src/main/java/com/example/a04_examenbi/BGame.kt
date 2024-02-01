package com.example.a04_examenbi

import android.widget.EditText

data class BGame(
    var id: Int?,
    var nombre: String,
    var fechaLanzamiento: String,
    var precio: Double,
    var esMultiplayer: Boolean,
    var idDeveloper: Int
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

