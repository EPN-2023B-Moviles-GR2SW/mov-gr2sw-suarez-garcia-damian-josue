package com.example.a04_examenbi

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.EditText
import android.widget.Spinner
import android.widget.TextView
import com.google.android.material.snackbar.Snackbar

class Game_edicion : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_game_edicion)

        val idDeveloperGame = intent.extras?.getInt("idDeveloper")
        val idGame = intent.extras?.getInt("id")
        val nombreGame = intent.extras?.getString("nombreGame")
        val fechaLanzamientoGame = intent.extras?.getString("fechaLanzamientoGame")
        val esMultiplayer = intent.extras?.getString("esMultiplayer")
        val precioGame = intent.extras?.getString("precioGame")


        /*Definicion del combo box para multiplayer*/
        val spinnerMultiplayer = findViewById<Spinner>(R.id.sp_esmultiplayer_edit)

        val adaptador = ArrayAdapter.createFromResource(
            this, // contexto,
            R.array.items_multiplayer,
            android.R.layout.simple_spinner_item // como se va a ver (XML).
        )
        adaptador.setDropDownViewResource(android.R.layout.simple_spinner_item)
        spinnerMultiplayer.adapter = adaptador
        /*Fin definicion spinner*/

        findViewById<TextView>(R.id.txt_juego_titulo).setText("Juego a modificar: ${nombreGame}")

        if (idGame!= null && idDeveloperGame != null) {

            val GameEdicion = BDDconection.bddAplication!!.consultarGamePorIdyDeveloper(idGame, idDeveloperGame)

            val nombre = findViewById<EditText>(R.id.et_nombre_game_edit)
            val fechaLanzamiento = findViewById<EditText>(R.id.et_fecha_lanzamiento_edit)
            val precio = findViewById<EditText>(R.id.et_precio_game_edit)


            nombre.setText(GameEdicion.nombre)
            fechaLanzamiento.setText(GameEdicion.fechaLanzamiento)
            precio.setText(GameEdicion.precio.toString())

            // Configura el Spinner con el valor de platoPrincipal
            val esMultiplayerArray = resources.getStringArray(R.array.items_multiplayer)

            val esMultiplayerPosition = if (GameEdicion.esMultiplayer) {
                esMultiplayerArray.indexOf("Si")
            } else {
                esMultiplayerArray.indexOf("No")
            }

            spinnerMultiplayer.setSelection(esMultiplayerPosition)

        }


        /*Edicion de Game*/
        val btnGuardarGame = findViewById<Button>(R.id.btn_guardar_game_edit)
            btnGuardarGame
                .setOnClickListener {
            try {
                val idGame = intent.extras?.getInt("id")
                val nombreGame = findViewById<EditText>(R.id.et_nombre_game_edit)
                val fechaLanzamiento = findViewById<EditText>(R.id.et_fecha_lanzamiento_edit)
                val esMultiplayer = spinnerMultiplayer.selectedItem.toString()
                val precio = findViewById<EditText>(R.id.et_precio_game_edit)

                // Limpiar errores anteriores
                nombreGame.error = null
                fechaLanzamiento.error = null
                precio.error = null

                if (validarCampos(nombreGame, fechaLanzamiento, esMultiplayer, precio)) {
                    val esMultiplayerValue= esMultiplayer.equals("Si")
                    val datosActualizados = BGame(
                        idGame,
                        nombreGame.text.toString(),
                        fechaLanzamiento.text.toString(),
                        precio.text.toString().toDouble(),
                        esMultiplayerValue,
                        idDeveloperGame!!
                    )

                    val respuesta = BDDconection
                        .bddAplication!!.actualizarGameIdyIdDeveloper(datosActualizados)

                    if (respuesta) {
                        val data = Intent()
                        /*data.putExtra("idDeveloper", idDeveloperGame)
                        data.putExtra("id", idGame)*/
                        data.putExtra("message", "Los datos del juego se han actualizado exitosamente")
                        setResult(RESULT_OK, data)
                        finish()
                        // Imprimir la representación en cadena del objeto actualizado
                        Log.d("Actualización Exitosa", datosActualizados.toString())
                    } else {
                        mostrarSnackbar("Hubo un problema al actualizar los datos del juego")
                    }
                }

            } catch (e: Exception) {
                Log.e("Error", "Error en la aplicación", e)
            }
        }

        //Configura los campos con los valores recibidos
        nombreGame?.let { findViewById<EditText>(R.id.et_nombre_game_edit).setText(it) }
        fechaLanzamientoGame?.let { findViewById<EditText>(R.id.et_fecha_lanzamiento_edit).setText(it) }
        esMultiplayer?.let {
            val esMultiplayerArray = resources.getStringArray(R.array.items_multiplayer)
            val esMultiplayerPosition = if (it.toBoolean()) {
                esMultiplayerArray.indexOf("Si")
            } else {
                esMultiplayerArray.indexOf("No")
            }
            spinnerMultiplayer.setSelection(esMultiplayerPosition)
        }
        precioGame?.let { findViewById<EditText>(R.id.et_precio_game_edit).setText(it) }
    }

    fun mostrarSnackbar(texto: String) {
        Snackbar
            .make(
                findViewById(R.id.constraint_edicion_game), //view
                texto, //texto
                Snackbar.LENGTH_LONG //tiwmpo
            )
            .show()
    }

    fun validarCampos(
        nombre: EditText,
        fechaLanzamiento: EditText,
        esMultipalyer: String,
        precio: EditText
    ): Boolean {

        if (nombre.text.isBlank()) {
            nombre.error = "Campo requerido"
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

        if (precio.text.isBlank()) {
            precio.error = "Campo requerido"
            return false
        } else {
            val precioDouble = precio.text.toString().toDouble()
            if (precioDouble < 0) {
                precio.error = "El precio debe ser un número mayor a 0"
                return false
            }
        }

        if(esMultipalyer.equals("--Seleccionar--", ignoreCase = true)){
            mostrarSnackbar("Porfavor especifique si es Multiplayer o no")
            return false
        }


        return true
    }

}