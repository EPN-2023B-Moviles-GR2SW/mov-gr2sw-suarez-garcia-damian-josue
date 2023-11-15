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
    val edadEjemplo: Int =12
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

    val esSoltero = (estadoCivilWhen == 'S')
    val coqueto if (esSoltero) "Si" else "No"

    //parametros

    calcularSueldo(10.00)
    calcularSueldo(10.00,15.00,20.00)
    calcularSueldo(10.00, bonoEspecial = 20.00)
    calcularSueldo(bonoEspecial = 20.00, sueldo=10.00, tasa= 14.00)

    //clase khotlin
    val sumaUno = Suma(1,1)
    val sumaDos = Suma(null,1)
    val sumaTres= Suma(1,null)

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

}


