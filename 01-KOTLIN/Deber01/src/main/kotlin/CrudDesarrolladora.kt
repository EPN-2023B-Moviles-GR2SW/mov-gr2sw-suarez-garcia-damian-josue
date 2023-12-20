import CrudJuego.leerJuegos
import java.io.*

object CrudDesarrolladora {
    private const val DESARROLLADORAS_FILE = "desarrolladoras.dat"

    fun crearDesarrolladora(desarrolladora: Desarrolladora) {
        val desarrolladoras = leerDesarrolladoras()
        desarrolladoras.add(desarrolladora)
        guardarDesarrolladoras(desarrolladoras)
    }

   private  fun leerDesarrolladoras(): MutableList<Desarrolladora> {
        return try {
            ObjectInputStream(FileInputStream(DESARROLLADORAS_FILE)).use { it.readObject() as MutableList<Desarrolladora> }
        } catch (e: FileNotFoundException) {
            mutableListOf()
        }
    }

    private fun guardarDesarrolladoras(desarrolladoras: List<Desarrolladora>) {
        ObjectOutputStream(FileOutputStream(DESARROLLADORAS_FILE)).use { it.writeObject(desarrolladoras) }
    }

    fun mostrarDesarrolladoras() {
        val desarrolladoras = leerDesarrolladoras()
        desarrolladoras.forEach { println(it) }
    }

    fun actualizarDesarrolladora(id: Int, nuevaDesarrolladora: Desarrolladora) {
        val desarrolladoras = leerDesarrolladoras()
        val index = desarrolladoras.indexOfFirst { it.id == id }
        if (index != -1) {
            desarrolladoras[index] = nuevaDesarrolladora
            guardarDesarrolladoras(desarrolladoras)
        } else {
            println("Desarrolladora no encontrada.")
        }
    }

    fun eliminarDesarrolladora(id: Int) {
        val desarrolladoras = leerDesarrolladoras()
        val nuevoListado = desarrolladoras.filter { it.id != id }
        if (desarrolladoras.size != nuevoListado.size) {
            guardarDesarrolladoras(nuevoListado)
        } else {
            println("Desarrolladora no encontrada.")
        }
    }

    fun agregarJuego(desarrolladoraId: Int, juegoId: Int) {
        val desarrolladoras = leerDesarrolladoras()
        val juegos = leerJuegos()

        val desarrolladoraIndex = desarrolladoras.indexOfFirst { it.id == desarrolladoraId }
        val juegoIndex = juegos.indexOfFirst { it.id == juegoId }

        if (desarrolladoraIndex != -1 && juegoIndex != -1) {
            val relacion = RelacionDesarrolladoraJuego(
                GeneradorID.generarIDRelaciones(),
                desarrolladoraId,
                juegoId,
                desarrolladoras[desarrolladoraIndex].nombre,
                juegos[juegoIndex].nombre
            )

            CrudRelacionDesarrolladoraJuego.crearRelacion(relacion)
            desarrolladoras[desarrolladoraIndex].juegos.add(relacion)
            guardarDesarrolladoras(desarrolladoras)
        } else {
            println("Desarrolladora o juego no encontrado.")
        }
    }

}
