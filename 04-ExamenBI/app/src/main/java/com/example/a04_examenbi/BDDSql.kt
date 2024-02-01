        package com.example.a04_examenbi
        
        import android.content.ContentValues
        import android.content.Context
        import android.database.sqlite.SQLiteDatabase
        import android.database.sqlite.SQLiteOpenHelper

        class BDDSql (
            contexto: Context?, //This
        ): SQLiteOpenHelper(
            contexto,
            "developer_game", //nombre BDD
            null,
            1
        ) {
            override fun onCreate(db: SQLiteDatabase?) {
                val scriptSQLCrearTablaDeveloper =
                    """
                        CREATE TABLE DEVELOPER(
                        id INTEGER PRIMARY KEY AUTOINCREMENT,
                        nombre VARCHAR(50),
                        fechaFundacion TEXT,
                        totalJuegos INTEGER,
                        ingresosAnuales DOUBLE
                        )
                    """.trimIndent()
                db?.execSQL(scriptSQLCrearTablaDeveloper)
        
                val scriptSQLCrearTablaGame =
                    """
                    CREATE TABLE GAME(
                        id INTEGER PRIMARY KEY AUTOINCREMENT,
                        nombre VARCHAR(50),
                        fechaLanzamiento TEXT,
                        precio DOUBLE,
                        esMultiplayer INTEGER,
                        idDeveloper INTEGER,
                        CONSTRAINT fk_developers
                            FOREIGN KEY (idDeveloper)
                            REFERENCES DEVELOPER(id)
                            ON DELETE CASCADE
                    )
                    """.trimIndent()
                db?.execSQL(scriptSQLCrearTablaGame)
            }
        
            override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
                if (oldVersion < 1) {
                    // Elimina las tablas si existen
                    db?.execSQL("DROP TABLE IF EXISTS GAME")
                    db?.execSQL("DROP TABLE IF EXISTS DEVELOPER")
        
                    val scriptSQLCrearTablaDeveloper =
                        """
                        CREATE TABLE DEVELOPER(
                        id INTEGER PRIMARY KEY AUTOINCREMENT,
                        nombre VARCHAR(50),
                        fechaFundacion TEXT,
                        totalJuegos INTEGER,
                        ingresosAnuales DOUBLE
                        )
                    """.trimIndent()
                    db?.execSQL(scriptSQLCrearTablaDeveloper)
        
                    val scriptSQLCrearTablaGame =
                        """
                    CREATE TABLE GAME(
                        id INTEGER PRIMARY KEY AUTOINCREMENT,
                        nombre VARCHAR(50),
                        fechaLanzamiento TEXT,
                        precio DOUBLE,
                        esMultiplayer INTEGER,
                        idDeveloper INTEGER,
                        CONSTRAINT fk_developers
                            FOREIGN KEY (idDeveloper)
                            REFERENCES DEVELOPER(id)
                            ON DELETE CASCADE
                    )
                    """.trimIndent()
                    db?.execSQL(scriptSQLCrearTablaGame)
                }
            }
        
            /* CRUD DEVELOPER*/
            fun crearDeveloper(newDeveloper: BDeveloper): Boolean {
                val basedatosEscritura = writableDatabase
                val valoresAGuardar = ContentValues()
        
                valoresAGuardar.put("id", newDeveloper.id)
                valoresAGuardar.put("nombre", newDeveloper.nombre)
                valoresAGuardar.put("fechaFundacion", newDeveloper.fechaFundacion)
                valoresAGuardar.put("totalJuegos", newDeveloper.totalJuegos)
                valoresAGuardar.put("ingresosAnuales", newDeveloper.ingresosAnuales)
        
                val resultadoGuardar = basedatosEscritura
                        .insert(
                            "DEVELOPER", //nombre de la tabla
                            null,
                            valoresAGuardar //valores
                        )
        
                    basedatosEscritura.close()
                    return if (resultadoGuardar.toInt() == -1) false else true
                }
        
                fun obtenerDeveloper(): ArrayList<BDeveloper> {
                    val baseDatosLectura = readableDatabase
        
                    val scriptConsultaLectura = """
                    SELECT * FROM DEVELOPER
                """.trimIndent()
        
                    val resultadoConsulta = baseDatosLectura.rawQuery(scriptConsultaLectura, null)
        
                    val developers = arrayListOf<BDeveloper>()
        
                    if(resultadoConsulta != null && resultadoConsulta.moveToFirst()){
        
                        do{
                            val id = resultadoConsulta.getInt(0) //Indice 0
                            val nombre = resultadoConsulta.getString(1)
                            val fechaFundacion= resultadoConsulta.getString(2)
                            val totalJuegos = resultadoConsulta.getInt(3)
                            val ingresosAnuales = resultadoConsulta.getDouble(4)
        
        
                            if(id != null){
                                val usuarioEncontrado = BDeveloper(0, "", "2000-01-01", 0, 0.0)
                                usuarioEncontrado.id = id
                                usuarioEncontrado.nombre = nombre
                                usuarioEncontrado.fechaFundacion = fechaFundacion
                                usuarioEncontrado.totalJuegos = totalJuegos
                                usuarioEncontrado.ingresosAnuales = ingresosAnuales
        
                                developers.add(usuarioEncontrado)
                            }
                        } while (resultadoConsulta.moveToNext())
                    }
        
                    resultadoConsulta?.close()
                    baseDatosLectura.close()
                    return developers //arreglo
                }
        
                fun consultarDevoloperId(idDeveloper: Int): BDeveloper{
                    val baseDatosLectura = readableDatabase
        
                    val scriptConsultaLectura = """
                    SELECT * FROM DEVELOPER WHERE ID = ?
                """.trimIndent()
        
                    val parametrosConsulta = arrayOf(idDeveloper.toString())
        
                    val resultadoConsulta = baseDatosLectura.rawQuery(
                        scriptConsultaLectura, //Consulta
                        parametrosConsulta //Parametros
                    )
        
                    val existeUsuario = resultadoConsulta.moveToFirst()
        
                    val usuarioEncontrado = BDeveloper(0, "", "2000-01-01", 0, 0.0)
                    if(existeUsuario){
                        do{
                            val id = resultadoConsulta.getInt(0) //Indice 0
                            val nombre = resultadoConsulta.getString(1)
                            val fechaFundacion= resultadoConsulta.getString(2)
                            val totalJuegos = resultadoConsulta.getInt(3)
                            val ingresosAnuales = resultadoConsulta.getDouble(4)
        
                            if(id != null){
                                usuarioEncontrado.id = id
                                usuarioEncontrado.nombre = nombre
                                usuarioEncontrado.fechaFundacion = fechaFundacion
                                usuarioEncontrado.totalJuegos = totalJuegos
                                usuarioEncontrado.ingresosAnuales = ingresosAnuales
                            }
                        } while (resultadoConsulta.moveToNext())
                    }
        
                    resultadoConsulta.close()
                    baseDatosLectura.close()
                    return usuarioEncontrado //arreglo
                }
        
                fun actualizarDeveloeprId(
                    datosActualizados: BDeveloper
                ): Boolean{
                    val conexionEscritura = writableDatabase
                    val valoresAActualizar = ContentValues()
                    valoresAActualizar.put("id", datosActualizados.id)
                    valoresAActualizar.put("nombre", datosActualizados.nombre)
                    valoresAActualizar.put("fechaFundacion", datosActualizados.fechaFundacion)
                    valoresAActualizar.put("totalJuegos", datosActualizados.totalJuegos)
                    valoresAActualizar.put("ingresosAnuales", datosActualizados.ingresosAnuales)
        
        
                    //where id = ?
                    val parametrosConsultaActualizar = arrayOf(datosActualizados.id.toString())
                    val resultadoActualizcion = conexionEscritura
                        .update(
                            "DEVELOPER", //tabla
                            valoresAActualizar,
                            "ID = ?",
                            parametrosConsultaActualizar
                        )
                    conexionEscritura.close()
                    return if (resultadoActualizcion == -1) false else true
                }
        
                fun eliminarDeveloperId(id: Int): Boolean{
                    val conexionEscritura = writableDatabase
        
                    val parametrosConsultaDelete = arrayOf( id.toString())
        
                    val resultadoEliminacion = conexionEscritura
                        .delete(
                            "DEVELOPER", //tabla
                            "ID = ?",
                            parametrosConsultaDelete
                        )
        
                    conexionEscritura.close()
                    return if(resultadoEliminacion == -1) false else true
                }
        
                /* CRUD GAME */
        
                fun crearGame(newGame: BGame): Boolean{
                    val basedatosEscritura = writableDatabase
                    val valoresAGuardar = ContentValues()
        
                    valoresAGuardar.put("id", newGame.id)
                    valoresAGuardar.put("nombre", newGame.nombre)
                    valoresAGuardar.put("fechaLanzamiento", newGame.fechaLanzamiento)
                    valoresAGuardar.put("precio", newGame.precio )
                    valoresAGuardar.put("esMultiplayer", if(newGame.esMultiplayer) 1 else 0)
                    valoresAGuardar.put("idDeveloper", newGame.idDeveloper)
        
                    val resultadoGuardar = basedatosEscritura
                        .insert(
                            "GAME", //nombre de la tabla
                            null,
                            valoresAGuardar //valores
                        )
        
                    basedatosEscritura.close()
                    return if (resultadoGuardar.toInt() == -1) false else true
                }
        
                fun obtenerGamesPorDeveloper(idDeveloper: Int): ArrayList<BGame> {
                    val games = arrayListOf<BGame>()
                    val baseDatosLectura = readableDatabase
        
                    val scriptConsultaLectura = """
                    SELECT * FROM GAME
                    WHERE IDDEVELOPER = ?
                """.trimIndent()
        
                    val parametrosConsultaLectura = arrayOf(idDeveloper.toString())
                    val resultadoConsultaLectura = baseDatosLectura.rawQuery(
                        scriptConsultaLectura, //Consulta
                        parametrosConsultaLectura //Parametros
                    )
        
                    if(resultadoConsultaLectura != null && resultadoConsultaLectura.moveToFirst()){
        
                        do{
                            val id = resultadoConsultaLectura.getInt(0)
                            val nombre = resultadoConsultaLectura.getString(1)
                            val fechaLanzamiento = resultadoConsultaLectura.getString(2)
                            val precio = resultadoConsultaLectura.getDouble(3)
                            val esMultipalyer = resultadoConsultaLectura.getString(4)
                            val idDeveloper = resultadoConsultaLectura.getInt(5)
        
                            if(id != null){
                                val gameEncontrado = BGame(0, "", "2000-01-01", 0.0, true, 0)
                                gameEncontrado.id = id
                                gameEncontrado.nombre = nombre
                                gameEncontrado.fechaLanzamiento = fechaLanzamiento
                                gameEncontrado.precio = precio
                                gameEncontrado.esMultiplayer = esMultipalyer.equals("1")
                                gameEncontrado.idDeveloper= idDeveloper
        
                                games.add(gameEncontrado)
                            }
                        } while (resultadoConsultaLectura.moveToNext())
                    }
        
                    resultadoConsultaLectura?.close()
                    baseDatosLectura.close()
        
                    return games//arreglo
                }
        
                fun consultarGamePorIdyDeveloper(idGame: Int, idDeveloper: Int): BGame{
                    val baseDatosLectura = readableDatabase
        
                    val scriptConsultaLectura = """
                    SELECT * FROM GAME WHERE ID = ? AND IDDEVELOPER = ?
                """.trimIndent()
        
                    val parametrosConsultaLectura = arrayOf(idGame.toString(), idDeveloper.toString())
        
                    val resultadoConsultaLectura = baseDatosLectura.rawQuery(
                        scriptConsultaLectura, //Consulta
                        parametrosConsultaLectura //Parametros
                    )
        
                    val existeGame = resultadoConsultaLectura.moveToFirst()
        
                    val gameEncontrado = BGame(0, "", "2000-01-01", 0.0, true, 0)
                    if(existeGame){
                        do{
                            val id = resultadoConsultaLectura.getInt(0)
                            val nombre = resultadoConsultaLectura.getString(1)
                            val fechaLanzamiento = resultadoConsultaLectura.getString(2)
                            val precio = resultadoConsultaLectura.getDouble(3)
                            val esMultipalyer = resultadoConsultaLectura.getString(4)
                            val idDeveloper = resultadoConsultaLectura.getInt(5)
        
                            if(id != null){
                                gameEncontrado.id = id
                                gameEncontrado.nombre = nombre
                                gameEncontrado.fechaLanzamiento = fechaLanzamiento
                                gameEncontrado.precio = precio
                                gameEncontrado.esMultiplayer = esMultipalyer.equals("1")
                                gameEncontrado.idDeveloper= idDeveloper
                            }
                        } while (resultadoConsultaLectura.moveToNext())
                    }
        
                    resultadoConsultaLectura.close()
                    baseDatosLectura.close()
                    return gameEncontrado //arreglo
                }
        
                fun actualizarGameIdyIdDeveloper(
                    datosActualizados: BGame
                ): Boolean{
                    val conexionEscritura = writableDatabase
                    val valoresAActualizar = ContentValues()
                    valoresAActualizar.put("id", datosActualizados.id)
                    valoresAActualizar.put("nombre", datosActualizados.nombre)
                    valoresAActualizar.put("fechaLanzamiento", datosActualizados.fechaLanzamiento)
                    valoresAActualizar.put("precio", datosActualizados.precio)
                    valoresAActualizar.put("esMultiplayer", if(datosActualizados.esMultiplayer) 1 else 0)
                    valoresAActualizar.put("idDeveloper", datosActualizados.idDeveloper)
        
                    //where id = ?
                    val parametrosConsultaActualizarGame = arrayOf(datosActualizados.id.toString(), datosActualizados.idDeveloper.toString())
                    val resultadoActualizacion = conexionEscritura
                        .update(
                            "GAME", //tabla
                            valoresAActualizar,
                            "ID = ? and IDDEVELOPER = ?",
                            parametrosConsultaActualizarGame
                        )
                    conexionEscritura.close()
                    return if (resultadoActualizacion == -1) false else true
                }
        
                fun eliminarGamePorIdYIdDeveloper(idGame: Int, idDeveloper: Int): Boolean{
                    val conexionEscritura = writableDatabase
        
                    val parametrosConsultaDelete = arrayOf( idGame.toString(), idDeveloper.toString())
        
                    val resultadoEliminacion = conexionEscritura
                        .delete(
                            "GAME", //tabla
                            "ID = ? and IDDEVELOPER = ?",
                            parametrosConsultaDelete
                        )
        
                    conexionEscritura.close()
                    return if(resultadoEliminacion == -1) false else true
                }
        
            }
        
        
