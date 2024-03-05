package com.example.a04_examenbi
import com.example.a04_examenbi.BDeveloper
import com.example.a04_examenbi.BGame
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.google.android.gms.tasks.Task
import com.google.firebase.firestore.DocumentSnapshot

class BDDFirestore {
    val db = Firebase.firestore

    fun crearDeveloper(newDeveloper: BDeveloper): Task<Void> {
        val documentReference = db.collection("developers").document(newDeveloper.id.toString())
        return documentReference.set(newDeveloper)
    }

    fun obtenerDevelopers(callback: (ArrayList<BDeveloper>) -> Unit) {
        val developerList = ArrayList<BDeveloper>()
        db.collection("developers")
            .get()
            .addOnSuccessListener { result ->
                for (document in result) {
                    val developer = document.toObject(BDeveloper::class.java)
                    developer.id = document.id.toInt()
                    developerList.add(developer)
                }
                callback(developerList)
            }
            .addOnFailureListener { exception ->
                // Manejar errores
            }
    }

    fun consultarDeveloperPorId(id: String): Task<DocumentSnapshot> {
        val documentReference = db.collection("developers").document(id)
        return documentReference.get()
    }

    fun actualizarDeveloperId(datosActualizados: BDeveloper): Task<Void> {
        val documentReference = db.collection("developers").document(datosActualizados.id.toString())
        val data = hashMapOf(
            "nombre" to datosActualizados.nombre,
            "totalJuegos" to datosActualizados.totalJuegos,
            "fechaFundacion" to datosActualizados.fechaFundacion,
            "ingresosAnuales" to datosActualizados.ingresosAnuales
        )

        return documentReference.set(data)
    }


    fun eliminarDeveloperId(id: String): Task<Void> {
        val documentReference = db.collection("developers").document(id)
        return documentReference.delete()
    }

    //CRUD Games
    fun obtenerGamesPorDeveloper(identificadorDeveloper: Int, callback: (ArrayList<BGame>) -> Unit) {
        val games = ArrayList<BGame>()
        val collectionReference = db.collection("developers").document(identificadorDeveloper.toString()).collection("games")

        collectionReference.get()
            .addOnSuccessListener { querySnapshot ->
                for (document in querySnapshot.documents) {
                    val game = document.toObject(BGame::class.java)
                    game?.let {
                        // Convertir el ID del documento a Int
                        it.id = document.id.toIntOrNull() ?: 0 // Si la conversión falla, asigna 0 por defecto
                        games.add(it)
                    }
                }
                callback(games)
            }
            .addOnFailureListener { exception ->
                // Manejar errores
            }
    }

    fun crearGame(newGame: BGame): Task<Void> {
        // Referencia al documento del game
        val gameDocumentReference = db.collection("developers").document(newGame.idDeveloper.toString())

        // Referencia a la colección de juegos dentro del documento del developer
        val gamesCollectionReference = gameDocumentReference.collection("games")

        // Agregar la nueva comida a la colección de comidas
        val documentReference = gamesCollectionReference.document(newGame.id.toString())

        return documentReference.set(newGame)
    }

    fun consultarGamePorIdYDeveloper(identificador: Int, codigoUnicoCocinero: Int): Task<DocumentSnapshot> {
        // Referencia al documento del game dentro de la colección del developer
        val documentReference = db.collection("developers").document()
            .collection("games").document(identificador.toString())

        // Obtener la comida por identificador y código único del cocinero
        return documentReference.get()
    }

    fun actualizarGamePorIdentificadorYIdDeveloper(datosActualizados: BGame): Task<Void> {
        // Referencia al documento del game dentro de la colección del developer
        val documentReference = db.collection("developers").document(datosActualizados.id.toString())
            .collection("games").document(datosActualizados.id.toString())

        // Actualizar los datos de lo games en Firestore
        return documentReference.set(datosActualizados)
    }

    fun eliminarGamePorIdentificadorYIdDeveloper(codigoGame: Int, codigoDeveloper: Int): Task<Void> {
        // Referencia al documento del game dentro de la colección del developer
        val documentReference = db.collection("developers").document(codigoDeveloper.toString())
            .collection("games").document(codigoGame.toString())

        // Eliminar el documento de la comida de Firestore
        return documentReference.delete()
    }
}