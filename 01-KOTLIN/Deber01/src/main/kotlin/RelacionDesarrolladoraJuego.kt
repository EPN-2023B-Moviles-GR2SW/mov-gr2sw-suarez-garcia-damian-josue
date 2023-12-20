import java.io.Serializable

data class RelacionDesarrolladoraJuego(
    val id: Int,
    val desarrolladoraId: Int,
    val juegoId: Int,
    val nombreDesarrolladora: String,
    val nombreJuego: String
) : Serializable