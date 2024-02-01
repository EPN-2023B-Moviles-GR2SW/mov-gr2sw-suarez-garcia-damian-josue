package com.example.a04_examenbi

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.EditText
import android.widget.Spinner
import com.google.android.material.snackbar.Snackbar

class Game_creacion : AppCompatActivity() {
    private lateinit var bddSql: BDDSql

    var games = arrayListOf<BGame>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_game_creacion)

      // Inicialización de la base de datos
        val idDeveloper = intent.extras?.getInt("idDeveloper")

        /*Definicion del combo box para plato dia*/
        val spinnerMultiplayer = findViewById<Spinner>(R.id.sp_multiplayer)

        val adaptador = ArrayAdapter.createFromResource(
            this, // contexto,
            R.array.items_multiplayer,
            android.R.layout.simple_spinner_item // como se va a ver (XML).
        )
        adaptador.setDropDownViewResource(android.R.layout.simple_spinner_item)
        spinnerMultiplayer.adapter = adaptador
        /*Fin definicion spinner*/

            val btnCrearGame = findViewById<Button>(R.id.btn_crear_game_developer)
            btnCrearGame.setOnClickListener {
                try{
                    val nombreGame = findViewById<EditText>(R.id.et_nombre_game)
                    val fechaLanzamiento = findViewById<EditText>(R.id.et_fecha_lanzamiento)
                    val precio = findViewById<EditText>(R.id.et_precio_game)
                    val esMultiplayer = spinnerMultiplayer.selectedItem.toString()

                    nombreGame.error= null
                    fechaLanzamiento.error = null
                    precio.error = null


                    if(validarCampos(nombreGame,fechaLanzamiento,precio, esMultiplayer)){
                        val esMultiplayerValue = esMultiplayer.equals("Si")
                        val newGame = BGame(
                            null,
                            nombreGame.text.toString(),
                            fechaLanzamiento.text.toString(),
                            precio.text.toString().toDouble(),
                            esMultiplayerValue,
                            idDeveloper!!
                        )

                        val respuesta = BDDconection
                            .bddAplication!!.crearGame(newGame)

                        if(respuesta) {
                            val data = Intent()
                            data.putExtra("idDeveloper", idDeveloper.toString())
                            data.putExtra("message", "El juego se ha creado exitosamente")
                            setResult(RESULT_OK, data)
                            finish()
                        }else{
                            mostrarSnackbar("Hubo un problema al crear el juego")
                        }
                    }

                } catch (e: Exception) {
                    Log.e("Error", "Error en la aplicación", e)
                }
            }
    }

    fun mostrarSnackbar(texto:String){
        Snackbar
            .make(
                findViewById(R.id.constraint_creacion_game), //view
                texto, //texto
                Snackbar.LENGTH_LONG //tiwmpo
            )
            .show()
    }

    fun validarCampos(
        nombreGame: EditText,
        fechaLanzamiento: EditText,
        precioGame: EditText,
        esMultiplayer: String): Boolean {

        if (nombreGame.text.isBlank()) {
            nombreGame.error = "Campo requerido"
            return false
        }

        if (fechaLanzamiento.text.isBlank()) {
            fechaLanzamiento.error = "Campo requerido"
            return false
        } else {
            val fechaRegex = Regex("""^\d{4}-\d{2}-\d{2}$""")
            if (!fechaRegex.matches(fechaLanzamiento.text.toString())) {
                fechaLanzamiento.error = "Formato de fecha inválido (debe ser yyyy-MM-dd)"
                return false
            }
        }

        if (precioGame.text.isBlank()) {
            precioGame.error = "Campo requerido"
            return false
        } else {
            val precioDouble = precioGame.text.toString().toDouble()
            if (precioDouble < 0) {
                precioGame.error = "El precio debe ser un número mayor a 0"
                return false
            }
        }

        if (esMultiplayer.equals("--Seleccionar--", ignoreCase = true)) {
            mostrarSnackbar("Porfavor especifique si es muliplayer o no")
            return false
        }
        return true
    }
}
