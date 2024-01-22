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

class JuegoHome : AppCompatActivity() {

    lateinit var adaptador: ArrayAdapter<String>;
    lateinit var juegos:MutableList<BJuego>;
    private  var posicionJuegoSeleccionado = -1
    var desarrolladoraId =-1;
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_juego_home)

        val botonCrearJuego = findViewById<Button>(R.id.btn_crear_juego)
        botonCrearJuego.setOnClickListener{
            irActividad(FormJuego::class.java)
        }

        desarrolladoraId = intent.getIntExtra("JuegoId",-1)

        actualizarListaJuegos();
    }
    private fun actualizarListaJuegos() {
        var desarrolladora =  BDMemoria.buscarDesarrolladora(desarrolladoraId)!!;
        juegos = BDMemoria.cargarJuegos(desarrolladora);
        val listaJuegos = findViewById<ListView>(R.id.lv_juegos)
        adaptador = ArrayAdapter(
            this,
            android.R.layout.simple_list_item_1,
            juegos.mapIndexed { index, juego ->
                "${juego.nombre}"
            }
        )
        listaJuegos.adapter = adaptador;
        adaptador.notifyDataSetChanged();
        registerForContextMenu(listaJuegos);
    }

    override fun onCreateContextMenu(
        menu: ContextMenu?,
        v: View?,
        menuInfo: ContextMenu.ContextMenuInfo?
    ) {
        super.onCreateContextMenu(menu, v, menuInfo)
        var inflater = menuInflater
        inflater.inflate(R.menu.menu_juegos, menu)
        val informacionJuego = menuInfo as AdapterView.AdapterContextMenuInfo;
        val posicionJuego = informacionJuego.position;
        if(posicionJuego != null){
            posicionJuegoSeleccionado = posicionJuego;
        }
    }
    override fun onContextItemSelected(item: MenuItem): Boolean {
        return when(item.itemId){
            R.id.mi_editar_juego->{
                try {
                    val juegoId = posicionJuegoSeleccionado;
                    irActividad(FormJuego::class.java, juegoId, desarrolladoraId)
                } catch (e: Throwable){}
                true;
            }
            R.id.mi_eliminar_juego ->{
                eliminar()
                true
            }
            else -> super.onContextItemSelected(item)
        }
    }

    fun irActividad(clase:Class<*>){
        val intent = Intent(this,clase);
        intent.putExtra("desarrolladoraId",desarrolladoraId);
        actualizarListaJuegos()
        startActivity(intent)
    }

    fun irActividad(clase:Class<*>,juegoId:Int?,desarrolladoraId: Int?) {
        val intent = Intent(this, clase)
        intent.putExtra("juegoId", juegoId)
        intent.putExtra("desarrolladoraId", desarrolladoraId)
        startActivity(intent)
    }

    fun eliminar(){
        val builderDialog = AlertDialog.Builder(this)
        builderDialog.setTitle("¿Deseas eliminar al empleado?")
        builderDialog.setNegativeButton("Cancelar",null);
        builderDialog.setPositiveButton("Eliminar"){
                dialog,_ ->
            if(posicionJuegoSeleccionado.let { BDMemoria.eliminarJuego(desarrolladoraId, it) }){
                actualizarListaJuegos()
            }
        }
        val dialog = builderDialog.create();
        dialog.show();
    }

    override fun onRestart() {
        super.onRestart()
        actualizarListaJuegos()
    }

    override fun onResume() {
        super.onResume()
        actualizarListaJuegos()
    }
}