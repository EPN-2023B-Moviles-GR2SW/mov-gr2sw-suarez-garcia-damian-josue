// Main.kt

import java.text.SimpleDateFormat
import java.util.*

fun main() {
    val dateFormat = SimpleDateFormat("yyyy-MM-dd")
    val scanner = Scanner(System.`in`)

    // Menú principal
    while (true) {
        println("\n--- Menú Principal ---")
        println("1. Gestionar Desarrolladoras")
        println("2. Gestionar Juegos")
        println("3. Asignar Juego a Desarrolladora")
        println("4. Ver Relaciones")
        println("5. Salir")
        print("Seleccione una opción: ")

        when (scanner.nextInt()) {
            1 -> gestionarDesarrolladoras(scanner, dateFormat)
            2 -> gestionarJuegos(scanner, dateFormat)
            3 -> asignarJuegoADesarrolladora(scanner)
            4 -> verRelaciones()
            5 -> {
                println("Saliendo del programa. ¡Hasta luego!")
                break
            }
            else -> println("Opción no válida. Intente de nuevo.")
        }
    }
}


private fun gestionarDesarrolladoras(scanner: Scanner, dateFormat: SimpleDateFormat) {
    while (true) {
        println("\n--- Gestionar Desarrolladoras ---")
        println("1. Crear Desarrolladora")
        println("2. Mostrar Desarrolladoras")
        println("3. Actualizar Desarrolladora")
        println("4. Eliminar Desarrolladora")
        println("5. Volver al Menú Principal")
        print("Seleccione una opción: ")

        when (scanner.nextInt()) {
            1 -> {
                println("\n--- Crear Desarrolladora ---")
                scanner.nextLine()
                print("Ingrese el nombre: ")
                val nombre = scanner.nextLine()

                print("Ingrese la fecha de fundación (YYYY-MM-DD): ")
                val fechaFundacion = dateFormat.parse(scanner.next())

                print("Ingrese el total de juegos: ")
                val totalJuegos = scanner.nextInt()

                print("Ingrese los ingresos anuales: ")
                val ingresosAnuales = scanner.nextDouble()

                CrudDesarrolladora.crearDesarrolladora(
                    Desarrolladora(GeneradorID.generarIDDesarrolladoras(), nombre, fechaFundacion, totalJuegos, ingresosAnuales)
                )
                println("Desarrolladora creada exitosamente.")
            }
            2 -> {
                println("\n--- Mostrar Desarrolladoras ---")
                CrudDesarrolladora.mostrarDesarrolladoras()
            }
            3 -> {
                println("\n--- Actualizar Desarrolladora ---")
                print("Ingrese el ID de la desarrolladora a actualizar: ")
                val id = scanner.nextInt()

                scanner.nextLine()
                print("Ingrese el nuevo nombre: ")
                val nombre = scanner.nextLine()

                print("Ingrese la nueva fecha de fundación (YYYY-MM-DD): ")
                val fechaFundacion = dateFormat.parse(scanner.next())

                print("Ingrese el nuevo total de juegos: ")
                val totalJuegos = scanner.nextInt()

                print("Ingrese los nuevos ingresos anuales: ")
                val ingresosAnuales = scanner.nextDouble()

                CrudDesarrolladora.actualizarDesarrolladora(
                    id,
                    Desarrolladora(id, nombre, fechaFundacion, totalJuegos, ingresosAnuales)
                )
                println("Desarrolladora actualizada exitosamente.")
            }
            4 -> {
                println("\n--- Eliminar Desarrolladora ---")
                print("Ingrese el ID de la desarrolladora a eliminar: ")
                val id = scanner.nextInt()

                CrudDesarrolladora.eliminarDesarrolladora(id)
                println("Desarrolladora eliminada exitosamente.")
            }
            5 -> {
                println("Volviendo al Menú Principal.")
                break
            }
            else -> println("Opción no válida. Intente de nuevo.")
        }
    }
}

private fun gestionarJuegos(scanner: Scanner, dateFormat: SimpleDateFormat) {
    while (true) {
        println("\n--- Gestionar Juegos ---")
        println("1. Crear Juego")
        println("2. Mostrar Juegos")
        println("3. Actualizar Juego")
        println("4. Eliminar Juego")
        println("5. Volver al Menú Principal")
        print("Seleccione una opción: ")

        when (scanner.nextInt()) {
            1 -> {
                println("\n--- Crear Juego ---")
                scanner.nextLine()
                print("Ingrese el nombre del juego: ")
                val nombre = scanner.nextLine()

                print("Ingrese la fecha de lanzamiento (YYYY-MM-DD): ")
                val fechaLanzamiento = dateFormat.parse(scanner.next())

                print("Ingrese el precio: ")
                val precio = scanner.nextDouble()

                print("¿Es multiplayer? (true/false): ")
                val esMultiplayer = scanner.nextBoolean()

                CrudJuego.crearJuego(
                    Juego(GeneradorID.generarIDJuegos(), nombre, fechaLanzamiento, precio, esMultiplayer)
                )
                println("Juego creado exitosamente.")
            }
            2 -> {
                println("\n--- Mostrar Juegos ---")
                CrudJuego.mostrarJuegos()
            }
            3 -> {
                println("\n--- Actualizar Juego ---")
                print("Ingrese el ID del juego a actualizar: ")
                val id = scanner.nextInt()

                scanner.nextLine()
                print("Ingrese el nuevo nombre: ")
                val nombre = scanner.nextLine()

                print("Ingrese la nueva fecha de lanzamiento (YYYY-MM-DD): ")
                val fechaLanzamiento = dateFormat.parse(scanner.next())

                print("Ingrese el nuevo precio: ")
                val precio = scanner.nextDouble()

                print("¿Es multiplayer? (true/false): ")
                val esMultiplayer = scanner.nextBoolean()

                CrudJuego.actualizarJuego(
                    id,
                    Juego(id, nombre, fechaLanzamiento, precio, esMultiplayer)
                )
                println("Juego actualizado exitosamente.")
            }
            4 -> {
                println("\n--- Eliminar Juego ---")
                print("Ingrese el ID del juego a eliminar: ")
                val id = scanner.nextInt()

                CrudJuego.eliminarJuego(id)
                println("Juego eliminado exitosamente.")
            }
            5 -> {
                println("Volviendo al Menú Principal.")
                break
            }
            else -> println("Opción no válida. Intente de nuevo.")
        }
    }
}

private fun asignarJuegoADesarrolladora(scanner: Scanner) {
    println("\n--- Asignar Juego a Desarrolladora ---")
    print("Ingrese el ID de la Desarrolladora: ")
    val desarrolladoraId = scanner.nextInt()

    print("Ingrese el ID del Juego: ")
    val juegoId = scanner.nextInt()

    CrudDesarrolladora.agregarJuego(desarrolladoraId, juegoId)
    println("Juego asignado a la Desarrolladora exitosamente.")
}

private fun verRelaciones() {
    println("\n--- Mostrar Relaciones Desarrolladora-Juego ---")
    CrudRelacionDesarrolladoraJuego.mostrarRelaciones()
}