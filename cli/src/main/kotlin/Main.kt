package org.example
import kotlinx.cli.*
import java.io.File

private val file = "C:\\Users\\54911\\Desktop\\Tomy\\projects\\PrintScript\\cli\\yourfile.txt"
private val args = arrayOf("-o", "Validation", file)

fun main() {
    main2(args)
}

fun main2(args: Array<String>) {
    val parser = ArgParser("CLI-Tool")

    val operation by parser.option(ArgType.Choice<Operation>(), shortName = "o", description = "Operation to perform")
    val sourceFile by parser.argument(ArgType.String, description = "Source file")
    val version by parser.option(ArgType.String, shortName = "v", description = "Version of the file").default("1.0")
    val configFile by parser.option(ArgType.String, shortName = "c", description = "Configuration file for formatting")

    parser.parse(args)

    // Dependiendo de la operación seleccionada, llama a la función correspondiente
    when (operation) {
        Operation.Validation -> validate(sourceFile)
        Operation.Execution -> execute(sourceFile, version)
        Operation.Formatting -> format(sourceFile, configFile)
        Operation.Analyzing -> analyze(sourceFile)
        null -> TODO()
    }
}

enum class Operation { Validation, Execution, Formatting, Analyzing }

// Implementa el método validate (los otros quedan vacíos por ahora)
fun validate(sourceFile: String) {
    println("Validating $sourceFile...")
    // Lógica de validación
}

// Métodos vacíos para las otras operaciones
fun execute(sourceFile: String, version: String) {
    println("Executing $sourceFile with version $version...")
    // Lógica de ejecución
}

fun format(sourceFile: String, configFile: String?) {
    println("Formatting $sourceFile with config file $configFile...")
    // Lógica de formateo
}

fun analyze(sourceFile: String) {
    println("Analyzing $sourceFile...")
    // Lógica de análisis
}