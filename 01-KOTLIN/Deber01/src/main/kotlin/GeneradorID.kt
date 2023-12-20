// En tu objeto GeneradorID.kt

import java.io.*

object GeneradorID {
    private const val JUEGOS_ID_FILE = "last_juegos_id.dat"
    private const val DESARROLLADORAS_ID_FILE = "last_desarrolladoras_id.dat"
    private const val RELACIONES_ID_FILE = "last_relaciones_id.dat"

    private var juegosIDActual = leerUltimaID(JUEGOS_ID_FILE)
    private var desarrolladorasIDActual = leerUltimaID(DESARROLLADORAS_ID_FILE)
    private var relacionesIDActual = leerUltimaID(RELACIONES_ID_FILE)

    private fun leerUltimaID(idFile: String): Int {
        return try {
            ObjectInputStream(FileInputStream(idFile)).use { it.readInt() }
        } catch (e: FileNotFoundException) {
            1
        }
    }

    private fun guardarUltimaID(idFile: String, id: Int) {
        ObjectOutputStream(FileOutputStream(idFile)).use { it.writeInt(id) }
    }

    fun generarIDJuegos(): Int {
        val nuevaID = juegosIDActual++
        guardarUltimaID(JUEGOS_ID_FILE, juegosIDActual)
        return nuevaID
    }

    fun generarIDDesarrolladoras(): Int {
        val nuevaID = desarrolladorasIDActual++
        guardarUltimaID(DESARROLLADORAS_ID_FILE, desarrolladorasIDActual)
        return nuevaID
    }

    fun generarIDRelaciones(): Int {
        val nuevaID = relacionesIDActual++
        guardarUltimaID(RELACIONES_ID_FILE, relacionesIDActual)
        return nuevaID
    }
}
