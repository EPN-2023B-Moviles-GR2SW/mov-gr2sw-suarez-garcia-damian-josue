import java.io.Serializable
import java.util.*


data class Desarrolladora(
    val id: Int,
    val nombre: String,
    val fechaFundacion: Date,
    val totalJuegos: Int,
    val ingresosAnuales: Double,
    val juegos: MutableList<RelacionDesarrolladoraJuego> = mutableListOf()
) : Serializable