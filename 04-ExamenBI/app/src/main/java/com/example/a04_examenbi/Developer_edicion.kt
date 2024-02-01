package com.example.a04_examenbi

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import com.google.android.material.snackbar.Snackbar
import java.util.Date

class Developer_edicion : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_developer_edicion)

        val idDeveloperEdicion = intent.extras?.getInt("idDeveloper")
        val nombreDeveloper = intent.extras?.getString("nombreDeveloper")
        findViewById<TextView>(R.id.lbl_titulo_edicion_developer).text = nombreDeveloper

        if (idDeveloperEdicion != null) {
            val developerEdicion = BDDconection.bddAplication!!.consultarDevoloperId(idDeveloperEdicion)

            val nombre = findViewById<EditText>(R.id.txt_nombre_edit)
            val fechaFundacion = findViewById<EditText>(R.id.txt_fecha_fundacion_edit)
            val totalJuegos= findViewById<EditText>(R.id.txt_total_juegos_edit)
            val ingresoAnuales= findViewById<EditText>(R.id.txt_ingresos_anuales_edit)

            nombre.setText(developerEdicion.nombre)
            fechaFundacion.setText(developerEdicion.fechaFundacion)
            totalJuegos.setText(developerEdicion.totalJuegos.toString())
            ingresoAnuales.setText(developerEdicion.ingresosAnuales.toString())
        }

        /*Edicion de desarrollador*/
        val btnGuardarDeveloper = findViewById<Button>(R.id.btn_guardar_developer_edit)
        btnGuardarDeveloper.setOnClickListener {
            try {
                val nombre = findViewById<EditText>(R.id.txt_nombre_edit)
                val fechaFundacion = findViewById<EditText>(R.id.txt_fecha_fundacion_edit)
                val totalJuegos= findViewById<EditText>(R.id.txt_total_juegos_edit)
                val ingresoAnuales= findViewById<EditText>(R.id.txt_ingresos_anuales_edit)

                // Limpiar errores anteriores
                nombre.error = null
                fechaFundacion.error = null
                totalJuegos.error = null
                ingresoAnuales.error = null

                if (validarCampos(nombre, fechaFundacion, totalJuegos, ingresoAnuales)) {
                    val datosActualizados = BDeveloper(
                        idDeveloperEdicion,
                        nombre.text.toString(),
                        fechaFundacion.text.toString(),
                        totalJuegos.text.toString().toInt(),
                        ingresoAnuales.text.toString().toDouble()
                    )

                    val respuesta =
                        BDDconection.bddAplication!!.actualizarDeveloeprId(datosActualizados)

                    if (respuesta) {
                        val data = Intent()
                        data.putExtra(
                            "message",
                            "Los datos del desarrollador se han actualizado exitosamente"
                        )
                        setResult(RESULT_OK, data)
                        finish()
                    } else {
                        mostrarSnackbar("Hubo un problema al actualizar los datos")
                    }
                }
            } catch (e: Exception) {
                Log.e("Error", "Error en la aplicación", e)
            }
        }
    }

    private fun mostrarSnackbar(texto: String) {
        Snackbar
            .make(
                findViewById(R.id.layout_edicion_developer), //view
                texto, //texto
                Snackbar.LENGTH_LONG //tiempo
            )
            .show()
    }

    private fun validarCampos(
        nombre: EditText,
        fechaFundacion: EditText,
        totalJuegos: EditText,
        ingresosAnuales: EditText
    ): Boolean {

        if (nombre.text.isBlank()) {
            nombre.error = "Campo requerido"
            return false
        }

        if (fechaFundacion.text.isBlank()) {
            fechaFundacion.error = "Campo requerido"
            return false
        } else {
            val fechaRegex = Regex("""^\d{4}-\d{2}-\d{2}$""")
            if (!fechaRegex.matches(fechaFundacion.text.toString())) {
                fechaFundacion.error = "Formato de fecha inválido (debe ser yyyy-MM-dd)"
                return false
            }
        }

        if (totalJuegos.text.isBlank()) {
            totalJuegos.error = "Campo requerido"
            return false
        } else {
            val totalJuegosInt = totalJuegos.text.toString().toInt()
            if (totalJuegosInt < 0) {
                totalJuegos.error = "El total de juegos debe ser un número mayor que 0"
                return false
            }
        }

        if(ingresosAnuales.text.isBlank()){
            ingresosAnuales.error = "Campo requerido"
            return false
        }else{
            val salarioDouble = ingresosAnuales.text.toString().toDouble()
            if (salarioDouble <=0) {
                ingresosAnuales.error = "El ingreso anual debe ser un número mayor a 0"
                return false
            }
        }
        return true
    }
}