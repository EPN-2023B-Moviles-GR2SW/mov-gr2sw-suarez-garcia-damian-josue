import java.io.Serializable
import java.util.*

data class Juego(
    val id: Int,
    val nombre: String,
    val fechaLanzamiento: Date,
    val precio: Double,
    val esMultiplayer: Boolean,
    var desarrolladora: Desarrolladora? = null
) : Serializable