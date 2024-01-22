package com.example.a04_examenbi

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.ContextMenu
import android.view.MenuItem
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.ListView
import androidx.appcompat.app.AlertDialog
import androidx.core.content.ContextCompat
import androidx.core.content.ContextCompat.startActivity

class DesarrolladoraHome : AppCompatActivity() {

    lateinit var adaptador: ArrayAdapter<String>
    var posicionDesarrolldoraSeleccionada = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_desarrolladora_home)

        val botonCrearDesarrolladora = findViewById<Button>(R.id.btn_crear_desarrolladora)
        botonCrearDesarrolladora.setOnClickListener {
            irActividad(FormDesarrolladora::class.java)
        }

        actualizarListaDesarrolladora()

    }

    private fun actualizarListaDesarrolladora() {
        val listViewDesarrolladora = findViewById<ListView>(R.id.lv_lista_desarrolladora)
        adaptador = ArrayAdapter(
            this,
            android.R.layout.simple_list_item_1,
            BDMemoria.desarrolladoras.mapIndexed { index, desarrolladora ->
                "${desarrolladora.nombre}"
            }
        )
        listViewDesarrolladora.adapter = adaptador;
        adaptador.notifyDataSetChanged();
        registerForContextMenu(listViewDesarrolladora)
    }
    override fun onCreateContextMenu(
        menu: ContextMenu?,
        v: View?,
        menuInfo: ContextMenu.ContextMenuInfo?
    ) {
        super.onCreateContextMenu(menu, v, menuInfo)
        val inflater = menuInflater
        inflater.inflate(R.menu.menu_desarrolladora, menu)
        val informacion = menuInfo as AdapterView.AdapterContextMenuInfo
        val posicion = informacion.position
        posicionDesarrolldoraSeleccionada = posicion
    }

    override fun onContextItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.mi_ver_juegos -> {
                val desarrolladoraId =  posicionDesarrolldoraSeleccionada
                irActividad(DesarrolladoraHome::class.java, desarrolladoraId)
                true
            }
            R.id.mi_editar_desarrolladora -> {
                val empresaId = posicionDesarrolldoraSeleccionada
                irActividad(FormDesarrolladora::class.java, empresaId)
                true
            }
            R.id.mi_eliminar_desarrolladora -> {
                eliminar();
                true
            }
            else -> super.onContextItemSelected(item)
        }
    }

    private fun eliminar() {
        val desarrolladoraSeleccionada = BDMemoria.desarrolladoras.getOrNull(posicionDesarrolldoraSeleccionada)
        if (desarrolladoraSeleccionada != null) {
            val mensajeEliminar = AlertDialog.Builder(this)
            mensajeEliminar.setTitle("¿Desea eliminar la desarrolladora ${desarrolladoraSeleccionada.nombre}?")
            mensajeEliminar.setNegativeButton("Cancelar", null)
            mensajeEliminar.setPositiveButton("Eliminar") { _, _ ->
                if (BDMemoria.eliminarDesarrolladora(posicionDesarrolldoraSeleccionada)) {
                    actualizarListaDesarrolladora()
                }
            }
            val dialog = mensajeEliminar.create()
            dialog.show()
        }
    }

    fun irActividad(clase: Class<*>, desarrolladoraId: Int = -1) {
        val intent = Intent(this, clase)
        intent.putExtra("DesarrolladoraId", desarrolladoraId)
        ContextCompat.startActivity(this, intent, null)
    }
    override fun onRestart() {
        super.onRestart()
        actualizarListaDesarrolladora()
    }

    override fun onResume() {
        super.onResume()
        actualizarListaDesarrolladora()
    }

}

