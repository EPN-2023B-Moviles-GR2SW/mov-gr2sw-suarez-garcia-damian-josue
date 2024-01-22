package com.example.a04_examenbi

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.EditText
import java.text.SimpleDateFormat
import java.util.Locale

class FormDesarrolladora : AppCompatActivity() {

    lateinit var adaptador: ArrayAdapter<String>;

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_form_desarrolladora)
        cargarFormDesarrolladora();
        val botonAddDesarrolladora = findViewById<Button>(R.id.btn_add_desarrolladora)
        botonAddDesarrolladora.setOnClickListener(){
            addDearrolladora();
            finish();
        }
        val botonCancelarDesarrolladora = findViewById<Button>(R.id.btn_cancelar_desarrolladora)
        botonCancelarDesarrolladora.setOnClickListener({finish()})
    }

    fun addDearrolladora() {
        val idDesarrolladora = findViewById<EditText>(R.id.input_id_desarrolladora);
        val nombreDesarrolladora =findViewById<EditText>(R.id.input_nombre_desarrolladora);
        val fechaFundacion =findViewById<EditText>(R.id.input_fecha_desarrolladora);
        val totalJuegos =findViewById<EditText>(R.id.input_totalJuegos_desarrolladora);
        val ingresosAnuales =findViewById<EditText>(R.id.input_ingresos_desarrolladora);
        val desarrolladoraId = intent.getIntExtra("DesarrolladoraId",-1);

        // Parse fechaFundacion to Date
        val dateFormat = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
        val parsedDate = dateFormat.parse(fechaFundacion.text.toString())

        var desarrolladora = BDesarrolladora(
            id= idDesarrolladora.text.toString().toInt(),
            nombre = nombreDesarrolladora.text.toString(),
            fechaFundacion = parsedDate,
            totalJuegos = totalJuegos.text.toString().toInt(),
            ingresosAnuales = ingresosAnuales.text.toString().toDouble(),
        )
        if(desarrolladoraId == -1){
            BDMemoria.crearDesarrolladora(desarrolladora);
            setResult(RESULT_OK);
        }else{
            BDMemoria.actualizarDesarrolladora(desarrolladoraId,desarrolladora);
            setResult(RESULT_OK);
        }
    }

    fun cargarFormDesarrolladora() {
        val idDesarrolladora = findViewById<EditText>(R.id.input_id_desarrolladora);
        val nombreDesarrolladora =findViewById<EditText>(R.id.input_nombre_desarrolladora);
        val fechaFundacion =findViewById<EditText>(R.id.input_fecha_desarrolladora);
        val totalJuegos =findViewById<EditText>(R.id.input_totalJuegos_desarrolladora);
        val ingresosAnuales =findViewById<EditText>(R.id.input_ingresos_desarrolladora);
        val desarrolladoraId = intent.getIntExtra("DesarrolladoraId", -1);
        if (desarrolladoraId != -1) {
            val desarrolladora = BDMemoria.buscarDesarrolladora(desarrolladoraId)
            if (desarrolladora != null) {
                idDesarrolladora.setText(desarrolladora.id?.toString())
                nombreDesarrolladora.setText(desarrolladora.nombre)
                fechaFundacion.setText(desarrolladora.fechaFundacion?.toString())
                totalJuegos.setText(desarrolladora.totalJuegos?.toString())
                ingresosAnuales.setText(desarrolladora.ingresosAnuales?.toString())
            }
        }
    }

}

