package com.example.a04_examenbi

import android.app.Activity
import android.content.DialogInterface
import android.content.Intent
import android.os.Bundle
import android.view.ContextMenu
import android.view.MenuItem
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.ListView
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.snackbar.Snackbar

class MainActivity : AppCompatActivity() {

    var developers = arrayListOf<BDeveloper>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Inicialización de la base de datos
        //BDDconection.bddAplication = BDDSql(this)
        BDDconection.bddAplication = BDDFirestore()
        BDDconection.bddAplication!!.obtenerDevelopers{ developers ->
                this.developers= developers

                if(developers.size != 0){
                    //listado de cocineros
                    val listView = findViewById<ListView>(R.id.lv_list_developers)

                    val adaptador = ArrayAdapter(
                        this, // contexto
                        android.R.layout.simple_list_item_1, // como se va a ver (XML)
                        developers
                    )

                    listView.adapter = adaptador
                    adaptador.notifyDataSetChanged()
                    registerForContextMenu(listView)
                }else{
                    mostrarSnackbar("No existen cocineros")
                }
            }
        val btnCrearDeveloper = findViewById<Button>(R.id.id_btn_crear)
        btnCrearDeveloper.setOnClickListener {
            val intent = Intent(this, Developer_creacion::class.java)
            callbackContenidoIntentExplicito.launch(intent)
        }
    }

    fun mostrarSnackbar(texto:String){
        Snackbar
            .make(
                findViewById(R.id.constraint_developers), //view
                texto, //texto
                Snackbar.LENGTH_LONG //tiwmpo
            )
            .show()
    }

    // Creación de las opciones de acción (editar, eliminar, ver games)
    var posicionItemSeleccionado = 0
    override fun onCreateContextMenu(
        menu: ContextMenu?,
        v: View?,
        menuInfo: ContextMenu.ContextMenuInfo?
    ) {
        super.onCreateContextMenu(menu, v, menuInfo)
        // Llenamos las opciones del menu
        val inflater = menuInflater
        inflater.inflate(R.menu.menu, menu)
        // Obtener el id del ArrayListSeleccionado
        val info = menuInfo as AdapterView.AdapterContextMenuInfo
        val posicion = info.position
        posicionItemSeleccionado = posicion
    }

    override fun onContextItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.mi_editar -> {
                val idDeveloper = developers[posicionItemSeleccionado].id
                val nombreDeveloper = developers[posicionItemSeleccionado].nombre
                val intent = Intent(this, Developer_edicion::class.java)
                intent.putExtra("idDeveloper", idDeveloper)
                intent.putExtra("nombreDeveloper", nombreDeveloper)
                callbackContenidoIntentExplicito.launch(intent)
                return true
            }
            R.id.mi_eliminar -> {
                val result: Boolean
                if (developers[posicionItemSeleccionado].id != null) {
                    result = abrirDialogo(developers[posicionItemSeleccionado].id!!)
                } else {
                    result = false
                }
                return result
            }

            R.id.mi_ver_games -> {
                val idDeveloper = developers[posicionItemSeleccionado].id
                val nombreDeveloper = developers[posicionItemSeleccionado].nombre
                val intent = Intent(this, Game_Listado::class.java)
                intent.putExtra("idDeveloper", idDeveloper)
                intent.putExtra("nombreDeveloper", nombreDeveloper)
                startActivity(intent)
                return true
            }
            else -> super.onContextItemSelected(item)
        }
    }

    fun abrirDialogo(idDeveloper: Int): Boolean {
        val builder = AlertDialog.Builder(this)
        builder.setTitle("Desea eliminar a este developer?")

        var eliminacionExitosa = false

        builder.setPositiveButton(
            "Aceptar",
            DialogInterface.OnClickListener { dialog, which ->

                BDDconection.bddAplication?.eliminarDeveloperId(idDeveloper.toString())
                    ?.addOnSuccessListener {
                        mostrarSnackbar("Cocinero eliminado exitosamente")
                        cargarListaDevelopers()  // Actualiza la lista después de la eliminación
                        eliminacionExitosa = true
                    }
                    ?.addOnFailureListener { e ->
                        mostrarSnackbar("No se pudo eliminar al cocinero")
                        eliminacionExitosa = false
                    }
            }
        )
        builder.setNegativeButton(
            "Cancelar",
            null
        )

        val dialogo = builder.create()
        dialogo.show()

        return eliminacionExitosa
    }

    fun irEdicionDeveloper(clase: Class<*>, datosExtras: Bundle? = null) {
        val intent = Intent(this, clase)
        if (datosExtras != null) {
            intent.putExtras(datosExtras)
        }
        callbackContenidoIntentExplicito.launch(intent)
    }

    val callbackContenidoIntentExplicito =
        registerForActivityResult(
            ActivityResultContracts.StartActivityForResult()
        ) { result ->
            if (result.resultCode == Activity.RESULT_OK) {
                if (result.data != null) {
                    val data = result.data
                    cargarListaDevelopers()
                    mostrarSnackbar("${data?.getStringExtra("message")}")
                }
            }
        }


    private fun cargarListaDevelopers() {
        // Cargar la lista de cocineros desde la base de datos y notificar al adaptador
        BDDconection.bddAplication!!.obtenerDevelopers { developers ->
            this.developers = developers

            val adaptador = ArrayAdapter(
                this,
                android.R.layout.simple_list_item_1,
                developers
            )
            val listView = findViewById<ListView>(R.id.lv_list_developers)
            listView.adapter = adaptador
            adaptador.notifyDataSetChanged()
            registerForContextMenu(listView)
        }
    }
}