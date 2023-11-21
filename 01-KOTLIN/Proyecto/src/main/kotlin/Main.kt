import java.util.Date

fun main(args: Array<String>) {
    println("Hello World!")
    //INMUTABLES
    val inmutable: String = "Adrian";

    //MUTABLES
    var mutable: String = "Vicente";
    mutable = "Adrian";

    //val>var
    //Duck type
    var ejemploVariable = "Damian Suarez"
    val edadEjemplo: Int = 12
    ejemploVariable.trim()
    //ejemploVariable = edadEjemplo

    //Variable primitiva
    val nombreEstudiante: String = "Damian Suarea"
    val sueldo: Double = 1.2
    val estadoCivil: Char = 'C'
    val mayorEdad: Boolean = true
    //Clases Java
    val fechasNacimiento: Date = Date()

    //SWITCH

    val estadoCivilWhen = "C"
    when (estadoCivilWhen) {
        ("C") -> {
            println("Casado")
        }

        "S" -> {
            println("Soltero")
        }

        else -> {
            println("No sabemos")
        }
    }

    val esSoltero = (estadoCivilWhen == "S")
    val coqueto = if (esSoltero) "Si" else "No"

    //parametros

    calcularSueldo(10.00)
    calcularSueldo(10.00, 15.00, 20.00)
    calcularSueldo(10.00, bonoEspecial = 20.00)
    calcularSueldo(bonoEspecial = 20.00, sueldo = 10.00, tasa = 14.00)

    //clase khotlin
    val sumaUno = Suma(1, 1)
    val sumaDos = Suma(null, 1)
    val sumaTres = Suma(1, null)
    val sumaCuatro = Suma(null, null)
    sumaUno.sumar()
    sumaDos.sumar()
    sumaTres.sumar()
    sumaCuatro.sumar()
    println(Suma.pi)
    println(Suma.elevarAlCuadrado(2))
    println(Suma.historialSumas)
    //Arreglo Estatico
    val arregloEstatico: Array<Int> = arrayOf<Int>(1, 2, 3)
    println(arregloEstatico)

    //Arreglo Dinamicos
    val arregloDinamico: ArrayList<Int> = arrayListOf<Int>(
        1, 2, 3, 4, 5, 6, 7, 8, 9, 10
    )
    println(arregloDinamico)
    arregloDinamico.add(11)
    arregloDinamico.add(12)
    println(arregloDinamico)

    //FOR EACH->UNIT
    val repuestaForEach: Unit = arregloDinamico
        .forEach { valorActual: Int ->
            println("Valor ${valorActual}")
        }
    //it (eso) significa el elemento iterado
    arregloDinamico.forEach { println("Valor actual: ${it}") }

    arregloEstatico
        .forEachIndexed { indice: Int, valorActual: Int ->
            println("Valor ${valorActual} Indice: ${indice}")
        }
    println(repuestaForEach)

    //MAP -> Muta el arreglo (cambia el arreglo)
    //1) Enciamos el nuevo vlaor de la iteracion
    //2) Nos devuelve es un NUEVO ARREGLO con los valores modificados

    val repuestaMap: List<Double> = arregloDinamico
        .map { valorActual: Int ->
            return@map valorActual.toDouble() + 100.0
        }
    println(repuestaMap)
    val repuestaMapDos = arregloDinamico.map { it + 15 }

    //FILTER -> FILTRAR EL ARREGLO
    //1) Devolver una expresion (TRUE O FALSE)
    //2) Nuevo arreglo Filtrado
    val repuestaFilter: List<Int> = arregloDinamico
        .filter { valorActual: Int ->
            val mayorACinco: Boolean = valorActual > 5
            return@filter mayorACinco
        }
    val repuestaFilterDos = arregloDinamico.filter{
        it <=5
    }

    println(repuestaFilter)
    println(repuestaFilterDos)

}

// void -> Unit
fun imprimirNombre(nombre: String): Unit{
    // "Nombre : " + nombre
    println("Nombre : ${nombre}") // template strings
}

fun calcularSueldo(
    sueldo: Double, //Requerido
    tasa: Double = 12.00, //Opcional (defecto)
    bonoEspecial: Double? = null, // Opcion:null -> nullable
): Double {
    //Int -> Int? (nullable)
    //String -> String? (nullable)
    // Date -> Date? (nuullable)
    if(bonoEspecial == null) {
        return sueldo * (100 / tasa)
    }else{
        return sueldo * (100/tasa) + bonoEspecial
    }
}

abstract class NumerosJava{
    protected val numeroUno: Int
    private val numeroDos: Int
    constructor(
        uno: Int,
        dos: Int
    ){
        this.numeroUno = uno
        this.numeroDos = dos
        println("Inicializando")
    }
}

//Propiedades
abstract class Numeros(//Constructo Primario
    protected val numeroUno: Int, ///propiedad de la calse protected numeros.numeroUno
    protected val numeroDos: Int
){
    init { //bloque contructor primario
        this.numeroUno; this.numeroDos; // this es opcional
        numeroUno;numeroDos //sin el this, es lo mismo
        println("Inicializacion")
    }
}

class Suma( //Constructor priamrio suma
    unoParametro: Int, //parametro
    dosParametro: Int, //parametro
): Numeros(unoParametro,dosParametro){//Extendiendo y mandando los parametros(super)
    init {
        this.numeroUno
        this.numeroDos
    }

    constructor( //segundo constructor
        uno: Int?,
        dos: Int
    ):this (
        if(uno == null) 0 else uno,
        dos
    )

    constructor( // tercer constructor
        uno: Int,
        dos: Int?
    ):this (
        uno,
        if(dos == null) 0 else dos
    )

    constructor(//  cuarto constructor
        uno: Int?, // parametros
        dos: Int? // parametros
    ) : this(  // llamada constructor primario
        if (uno == null) 0 else uno,
        if (dos == null) 0 else uno
    )

    // public por defecto, o usar private o protected
    public fun sumar(): Int {
        val total = numeroUno + numeroDos
        // Suma.agregarHistorial(total)
        agregarHistorial(total)
        return total
    }
    // Atributos y Metodos "Compartidos"
    companion object {
        // entre las instancias
        val pi = 3.14
        fun elevarAlCuadrado(num: Int): Int {
            return num * num
        }
        val historialSumas = arrayListOf<Int>()
        fun agregarHistorial(valorNuevaSuma:Int){
            historialSumas.add(valorNuevaSuma)
        }
    }
}





