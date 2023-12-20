import java.io.*

object CrudRelacionDesarrolladoraJuego {
    private const val RELACION_FILE = "relacion_desarrolladora_juego.dat"

    fun crearRelacion(relacion: RelacionDesarrolladoraJuego) {
        val relaciones = leerRelaciones()
        relaciones.add(relacion)
        guardarRelaciones(relaciones)
    }

    private fun leerRelaciones(): MutableList<RelacionDesarrolladoraJuego> {
        return try {
            ObjectInputStream(FileInputStream(RELACION_FILE)).use { it.readObject() as MutableList<RelacionDesarrolladoraJuego> }
        } catch (e: FileNotFoundException) {
            mutableListOf()
        }
    }

    private fun guardarRelaciones(relaciones: List<RelacionDesarrolladoraJuego>) {
        ObjectOutputStream(FileOutputStream(RELACION_FILE)).use { it.writeObject(relaciones) }
    }

    // Implementa las operaciones CRUD necesarias
    fun mostrarRelaciones() {
        println("\n--- Mostrar Relaciones Desarrolladora-Juego ---")
        val relaciones = leerRelaciones()
        for (relacion in relaciones) {
            println("ID: ${relacion.id}")
            println("Desarrolladora: ${relacion.nombreDesarrolladora}")
            println("Juego: ${relacion.nombreJuego}")
            println()
        }
    }
}
