package com.example.a04_examenbi

import java.util.Date

class BDMemoria {
    companion object {
        // DESARROLLADORAS
        val desarrolladoras = arrayListOf<BDesarrolladora>()

        fun crearDesarrolladora(desarrolladora: BDesarrolladora) {
            desarrolladoras.add(desarrolladora)
        }

        fun buscarDesarrolladora(desarrolladoraId: Int): BDesarrolladora? {
            val desarrolladora = desarrolladoras.getOrNull(desarrolladoraId)
            if (desarrolladora != null) {
                return desarrolladora
            } else {
                println("Desarrolladora no encontrada ${desarrolladoraId}")
                return null
            }
        }

        fun actualizarDesarrolladora(id: Int, desarrolladoraActualizada: BDesarrolladora) {
            val desarrolladora = desarrolladoras.getOrNull(id)
            if (desarrolladora != null) {
                println("Desarrolladora seleccionada: $desarrolladora")
                desarrolladoras[id] = desarrolladoraActualizada
                println("Desarrolladora actualizada: $desarrolladoraActualizada")
            } else {
                println("Desarrolladora no encontrada: $id")
            }
        }

        fun eliminarDesarrolladora(id: Int): Boolean {
            val desarrolladora = desarrolladoras.getOrNull(id)
            if (desarrolladora != null) {
                desarrolladoras.remove(desarrolladora)
                println("Desarrolladora eliminada: $desarrolladora")
                return true
            } else {
                println("Desarrolladora no encontrada $id")
                return false
            }
        }

        // JUEGOS
        fun cargarJuegos(desarrolladora: BDesarrolladora): MutableList<BJuego> {
            return desarrolladora.juegos.toMutableList()
        }

        fun crearJuego(desarrolladoraId: Int, juego: BJuego) {
            val desarrolladora = desarrolladoras.getOrNull(desarrolladoraId)
            if (desarrolladora != null) {
                desarrolladora.juegos.add(juego)
                println("Juego agregado '${desarrolladora.nombre}'.")
            } else {
                println("Desarrolladora no encontrada con:  $desarrolladoraId")
            }
        }

        fun actualizarJuego(desarrolladoraId: Int, juegoId: Int, juegoActualizado: BJuego) {
            val desarrolladora = desarrolladoras.getOrNull(desarrolladoraId)
            if (desarrolladora != null) {
                val juego = desarrolladora.juegos.getOrNull(juegoId)
                if (juego != null) {
                    desarrolladora.juegos[juegoId] = juegoActualizado
                } else {
                    println("Juego no encontrado con id: $juegoId")
                }
            } else {
                println("Desarrolladora no encontrada con id:  $desarrolladoraId")
            }
        }

        fun eliminarJuego(desarrolladoraId: Int, juegoId: Int): Boolean {
            val desarrolladora = desarrolladoras.getOrNull(desarrolladoraId)
            if (desarrolladora != null) {
                val juego = desarrolladora.juegos.getOrNull(juegoId)
                if (juego != null) {
                    desarrolladora.juegos.remove(juego)
                    println("Juego eliminado")
                    return true
                } else {
                    println("Juego no encontrado")
                    return false
                }
            } else {
                println("Desarrolladora no encontrada")
                return false
            }
        }

        init {
            // Desarrolladoras existentes
            desarrolladoras.add(
                BDesarrolladora(
                    1,
                    "GameTech",
                    Date(),
                    10,
                    1000000.0,
                    juegos = mutableListOf(
                        BJuego(1, "SuperGame", Date(), 49.99, true),
                        BJuego(2, "AdventureQuest", Date(), 29.99, true)
                    )
                )
            )

            desarrolladoras.add(
                BDesarrolladora(
                    2,
                    "PixelMaster",
                    Date(),
                    5,
                    500000.0,
                    juegos = mutableListOf(
                        BJuego(3, "PixelWorld", Date(), 19.99, false),
                        BJuego(4, "PixelRacer", Date(), 9.99, true)
                    )
                )
            )
        }
    }
}