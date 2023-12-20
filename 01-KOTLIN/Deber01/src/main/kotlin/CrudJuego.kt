import java.io.*

object CrudJuego {
    private const val JUEGOS_FILE = "juegos.dat"

    fun crearJuego(juego: Juego) {
        val juegos = leerJuegos()
        juegos.add(juego)
        guardarJuegos(juegos)
    }

    fun leerJuegos(): MutableList<Juego> {
        return try {
            ObjectInputStream(FileInputStream(JUEGOS_FILE)).use { it.readObject() as MutableList<Juego> }
        } catch (e: FileNotFoundException) {
            mutableListOf()
        }
    }

    private fun guardarJuegos(juegos: List<Juego>) {
        ObjectOutputStream(FileOutputStream(JUEGOS_FILE)).use { it.writeObject(juegos) }
    }

    fun mostrarJuegos() {
        val juegos = leerJuegos()
        juegos.forEach { println(it) }
    }

    fun actualizarJuego(id: Int, nuevoJuego: Juego) {
        val juegos = leerJuegos()
        val index = juegos.indexOfFirst { it.id == id }
        if (index != -1) {
            juegos[index] = nuevoJuego
            guardarJuegos(juegos)
        } else {
            println("Juego no encontrado.")
        }
    }

    fun eliminarJuego(id: Int) {
        val juegos = leerJuegos()
        val nuevoListado = juegos.filter { it.id != id }
        if (juegos.size != nuevoListado.size) {
            guardarJuegos(nuevoListado)
        } else {
            println("Juego no encontrado.")
        }
    }

}
