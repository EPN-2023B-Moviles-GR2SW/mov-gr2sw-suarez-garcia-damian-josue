package com.example.a04_examenbi

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import java.text.SimpleDateFormat
import java.util.Locale

class FormJuego : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_form_juego)

        llenarListaJuegos();

        val botonGuardarEmpleado = findViewById<Button>(R.id.btn_add_juego)
        botonGuardarEmpleado.setOnClickListener{
            addJuego();
            finish()
        }

        val botonCancelarJuego = findViewById<Button>(R.id.btn_cancelar_juego)
        botonCancelarJuego.setOnClickListener({finish()})
    }

    fun addJuego() {
        val idJuego = findViewById<EditText>(R.id.input_id_juego)
        val nombreJuego = findViewById<EditText>(R.id.input_nombre_juego)
        val fechaJuego = findViewById<EditText>(R.id.input_fecha_juego)
        val precioJuego = findViewById<EditText>(R.id.input_precio_juego)
        val multiplayerJuego = findViewById<EditText>(R.id.input_multiplayer_juego)

        // Parse fechaFundacion to Date
        val dateFormat = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
        val parsedDate = dateFormat.parse(fechaJuego.text.toString())

        var juego = BJuego(
            id = idJuego.text.toString().toInt(),
            nombre = nombreJuego.text.toString(),
            fechaLanzamiento = parsedDate,
            precio = precioJuego.text.toString().toDouble(),
            esMultiplayer = multiplayerJuego.text.toString().toBoolean()
        )

        val desarrolladoraId = intent.getIntExtra("DesarrolladoraId",-1);
        val juegoId = intent.getIntExtra("juegoId",-1);

        if(desarrolladoraId != -1 && juegoId !=-1){
            BDMemoria.actualizarJuego(desarrolladoraId,juegoId, juego);
        }else{
            BDMemoria.crearJuego(desarrolladoraId,juego)
        }
        setResult(RESULT_OK);
    }

    fun llenarListaJuegos() {
        val idJuego = findViewById<EditText>(R.id.input_id_juego)
        val nombreJuego = findViewById<EditText>(R.id.input_nombre_juego)
        val fechaJuego = findViewById<EditText>(R.id.input_fecha_juego)
        val precioJuego = findViewById<EditText>(R.id.input_precio_juego)
        val multiplayerJuego = findViewById<EditText>(R.id.input_multiplayer_juego)
        val desarrolladoraId = intent.getIntExtra("desarrolladoraId",-1);
        val juegoId = intent.getIntExtra("juegoId",-1);

        if(desarrolladoraId != -1 && juegoId !=-1){
            val desarrolladora  = BDMemoria.buscarDesarrolladora(desarrolladoraId);
            val juego = desarrolladora!!.juegos.get(juegoId);
            idJuego.setText(juego.id)
            nombreJuego.setText(juego.nombre);
            fechaJuego.setText(juego.fechaLanzamiento?.toString());
            precioJuego.setText(juego.precio.toString())
            multiplayerJuego.setText(juego.esMultiplayer.toString())
        }
    }
}

