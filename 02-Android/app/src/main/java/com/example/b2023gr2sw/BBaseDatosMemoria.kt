package com.example.b2023gr2sw

class BBaseDatosMemoria {
    companion object{
        val arregloBEntrenador = arrayListOf<BEntrenador>()
        init {
            arregloBEntrenador
                .add(
                    BEntrenador(1,"Adrian","a@a.com")
                )
            arregloBEntrenador
                .add(
                    BEntrenador(1,"Vicente","b@b.com")
                )
            arregloBEntrenador
                .add(
                    BEntrenador(1,"Carolina","c@c.com")
                )
        }
    }
}