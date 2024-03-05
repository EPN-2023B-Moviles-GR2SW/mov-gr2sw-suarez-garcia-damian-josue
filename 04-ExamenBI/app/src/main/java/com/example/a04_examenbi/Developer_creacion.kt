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
import java.text.SimpleDateFormat
import java.util.Date

class Developer_creacion : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_developer_creacion)

        val btnGuardarDeveloper = findViewById<Button>(R.id.btn_guardar_developer)
        btnGuardarDeveloper.setOnClickListener {
            try {
                val nombre = findViewById<EditText>(R.id.txt_nombre)
                val fechaFundacion = findViewById<EditText>(R.id.txt_datos_fecha_fundacion)
                val totalJuegos = findViewById<EditText>(R.id.txt_total_juegos)
                val ingresosAnuales = findViewById<EditText>(R.id.txt_ingresos_anuales)

                // Limpiar errores anteriores
                nombre.error = null
                fechaFundacion.error = null
                totalJuegos.error = null
                ingresosAnuales.error = null

                if (validarCampos(nombre, fechaFundacion, totalJuegos, ingresosAnuales)) {
                    val newDeveloper = BDeveloper(
                        0,
                        nombre.text.toString(),
                        fechaFundacion.text.toString(),
                        totalJuegos.text.toString().toInt(),
                        ingresosAnuales.text.toString().toDouble()
                    )

                    BDDFirestore().crearDeveloper(newDeveloper)
                        .addOnSuccessListener {
                            val data = Intent()
                            data.putExtra("message", "El developer se ha creado exitosamente")
                            setResult(RESULT_OK, data)
                            finish()
                        }
                        .addOnFailureListener { e ->
                            mostrarSnackbar("Hubo un problema en la creacion del developer")
                        }

                }
            } catch (e: Exception) {
                Log.e("Error", "Error en la aplicación", e)
            }
        }
    }

    fun mostrarSnackbar(texto: String) {
        Snackbar
            .make(
                findViewById(R.id.layout_creacion_developer), //view
                texto, //texto
                Snackbar.LENGTH_LONG //tiempo
            )
            .show()
    }

    fun validarCampos(
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
        }

        if (ingresosAnuales.text.isBlank()) {
            ingresosAnuales.error = "Campo requerido"
            return false
        }

        return true
    }

    /*override fun onDestroy() {
        BDDconection.bddAplication!!.close()
        super.onDestroy()
    }*/
}