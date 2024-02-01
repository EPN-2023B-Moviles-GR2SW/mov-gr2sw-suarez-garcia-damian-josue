package com.example.a04_examenbi

import android.app.Activity
import android.content.DialogInterface
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
import android.widget.TextView
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AlertDialog
import com.google.android.material.snackbar.Snackbar

class Game_Listado : AppCompatActivity() {
    var games = arrayListOf<BGame>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_game_listado)
        // Inicialización de la base de datos
        BDDconection.bddAplication = BDDSql(this)

        val idDeveloperGame = intent.extras?.getInt("idDeveloper")
        val nombreDeveloper = intent.extras?.getString("nombreDeveloper")

        findViewById<TextView>(R.id.txt_titulo_nombre_developer).text = nombreDeveloper

        if (idDeveloperGame != null) {
            games = BDDconection.bddAplication!!.obtenerGamesPorDeveloper(idDeveloperGame)
            if (games.size != 0) {
                // Listado de games
                val listView = findViewById<ListView>(R.id.lv_listados_games)

                val adaptador = ArrayAdapter(
                    this, // contexto
                    android.R.layout.simple_list_item_1, // como se va a ver (XML)
                    games
                )

                listView.adapter = adaptador
                adaptador.notifyDataSetChanged()
                registerForContextMenu(listView)
            } else {
                mostrarSnackbar("No existen games")
            }

            val btnCrearGame = findViewById<Button>(R.id.btn_crear_game)
            btnCrearGame.setOnClickListener {
                val extras = Bundle()
                extras.putInt("idDeveloper", idDeveloperGame)
                irActividad(Game_creacion::class.java, extras)
            }
        }
    }

    fun mostrarSnackbar(texto: String) {
        Snackbar
            .make(
                findViewById(R.id.constraint_games), // view
                texto, // texto
                Snackbar.LENGTH_LONG // tiempo
            )
            .show()
    }

    fun irActividad(clase: Class<*>, datosExtras: Bundle? = null) {
        val intent = Intent(this, clase)
        if (datosExtras != null) {
            intent.putExtras(datosExtras)
        }
        callbackContenidoIntentExplicito.launch(intent)
    }

    // Creación de las opciones de acción (editar, eliminar)
    var posicionItemSeleccionado = 0
    override fun onCreateContextMenu(
        menu: ContextMenu?,
        v: View?,
        menuInfo: ContextMenu.ContextMenuInfo?
    ) {
        super.onCreateContextMenu(menu, v, menuInfo)
        // Llenamos las opciones del menú
        val inflater = menuInflater
        inflater.inflate(R.menu.menu_games, menu)
        // Obtener el id del ArrayListSeleccionado
        val info = menuInfo as AdapterView.AdapterContextMenuInfo
        val posicion = info.position
        posicionItemSeleccionado = posicion
    }

    override fun onContextItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.mi_editar_game -> {
                val idGame = games.get(posicionItemSeleccionado).id
                val nombreGame = games[posicionItemSeleccionado].nombre
                val idDeveloperGame = games.get(posicionItemSeleccionado).idDeveloper

                val extras = Bundle()
                extras.putInt("idGame", idGame!!)
                extras.putString("nombreGame", nombreGame)
                extras.putInt("idDeveloper", idDeveloperGame)
                irActividad(Game_edicion::class.java, extras)
                return true
            }
            R.id.mi_eliminar_game -> {
                val idGame = games[posicionItemSeleccionado].id
                val idDeveloper = games[posicionItemSeleccionado].idDeveloper

                val result: Boolean = if (idGame != null) {
                    abrirDialogo(idGame, idDeveloper)
                } else {
                    false
                }
                return result
            }
            else -> super.onContextItemSelected(item)
        }
    }

    fun abrirDialogo(idGame: Int, idDeveloper: Int): Boolean {
        val builder = AlertDialog.Builder(this)
        builder.setTitle("¿Desea eliminar este game?")

        var eliminacionExitosa = false

        builder.setPositiveButton(
            "Aceptar",
            DialogInterface.OnClickListener { dialog, which ->

                val respuesta =
                    BDDconection.bddAplication?.eliminarGamePorIdYIdDeveloper(idGame, idDeveloper)

                if (respuesta == true) {
                    mostrarSnackbar("Game eliminado exitosamente")
                    cargarListaGames(idDeveloper)
                    eliminacionExitosa = true
                } else {
                    mostrarSnackbar("No se pudo eliminar este game")
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

    val callbackContenidoIntentExplicito =
        registerForActivityResult(
            ActivityResultContracts.StartActivityForResult()
        ) { result ->
            if (result.resultCode == Activity.RESULT_OK) {
                if (result.data != null) {
                    val data = result.data
                    cargarListaGames(intent.extras!!.getInt("idDeveloper"))
                    mostrarSnackbar("${data?.getStringExtra("message")}")
                }
            }
        }


            private fun cargarListaGames(idDeveloper: Int) {
        // Cargar la lista de games del developer desde la base de datos y notificar al adaptador
        games = BDDconection.bddAplication!!.obtenerGamesPorDeveloper(idDeveloper)
        val adaptador = ArrayAdapter(
            this,
            android.R.layout.simple_list_item_1,
            games
        )
        val listView = findViewById<ListView>(R.id.lv_listados_games)
        listView.adapter = adaptador
        adaptador.notifyDataSetChanged()
        registerForContextMenu(listView)
    }
}
