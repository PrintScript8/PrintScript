package cli

import me.tongfei.progressbar.ProgressBar
import runner.Operations
import java.io.FileInputStream
import java.util.Scanner

fun main() {
    val scanner = Scanner(System.`in`)

    var continueOperations = true
    var inputFile: String? = null  // Definir inputFile fuera del bucle

    while (continueOperations) {
        // Mostrar opciones al usuario
        println("Seleccione una acción:")
        println("1 -> Validate")
        println("2 -> Format")
        println("3 -> Execute")
        println("4 -> Analyze")
        println("5 -> Salir")

        // Leer la opción seleccionada
        val action = when (scanner.nextInt()) {
            1 -> "validate"
            2 -> "format"
            3 -> "execute"
            4 -> "analyze"
            5 -> {
                println("Saliendo...")
                break
            }
            else -> {
                println("Opción no válida. Intente nuevamente...")
                continue
            }
        }

        // Preguntar si desea usar el mismo archivo o uno diferente
        if (inputFile == null || askForNewFile(scanner)) {
            println("Ingrese la ruta al archivo de entrada:")
            inputFile = scanner.next()
        }

        // Cargar el archivo
        val version = "1.1"
        val inputStream = FileInputStream(inputFile)
        val operations = Operations(inputStream, version)

        // En caso de format o analyze, pedir JSON al usuario
        var jsonInput: String? = null
        if (action == "format" || action == "analyze") {
            println("Ingrese el JSON para $action:")
            scanner.nextLine() // Limpiar el buffer
            jsonInput = scanner.nextLine()
        }

        // Ejecutar la acción
        when (action) {
            "validate" -> validateCode(operations)
            "format" -> formatCode(operations, jsonInput)
            "execute" -> executeCode(operations)
            "analyze" -> analyzeCode(operations, jsonInput)
        }

        // Preguntar si desea continuar con otra operación
        println("¿Desea realizar otra operación? (s/n):")
        val continueResponse = scanner.next()
        continueOperations = continueResponse.equals("s", ignoreCase = true)
    }
}

fun askForNewFile(scanner: Scanner): Boolean {
    println("¿Desea usar el mismo archivo? (s/n):")
    val response = scanner.next()
    return response.equals("n", ignoreCase = true)
}

fun validateCode(operations: Operations) {
    try {
        println("Validando código...")
        val totalSteps = 100
        ProgressBar("Validating", totalSteps.toLong()).use { pb ->
            operations.validate()
            for (i in 1..totalSteps) {
                pb.step()
                Thread.sleep(50)
            }
            println("\n¡Validación completada!")
        }
    } catch (e: Exception) {
        println("Error en la validación: ${e.message}")
    }
}

fun formatCode(operations: Operations, jsonInput: String?) {
    try {
        println("Formateando código...")
        val totalSteps = 100
        ProgressBar("Formatting", totalSteps.toLong()).use { pb ->
            val formattedCode = operations.format(jsonInput ?: "")
            for (i in 1..totalSteps) {
                pb.step()
                Thread.sleep(50)
            }
            println("\nCódigo formateado:\n$formattedCode")
        }
    } catch (e: Exception) {
        println("Error en el formateo: ${e.message}")
    }
}

fun executeCode(operations: Operations) {
    try {
        println("Ejecutando código...")
        val totalSteps = 100
        ProgressBar("Executing", totalSteps.toLong()).use { pb ->
            val output = operations.execute().asSequence().toList()
            for (i in 1..totalSteps) {
                pb.step()
                Thread.sleep(50)
            }
            println("\nSalida de la ejecución:\n$output")
        }
    } catch (e: Exception) {
        println("Error en la ejecución: ${e.message}")
    }
}

fun analyzeCode(operations: Operations, jsonInput: String?) {
    try {
        println("Analizando código...")
        val totalSteps = 100
        ProgressBar("Analyzing", totalSteps.toLong()).use { pb ->
            val errors = operations.analyze(jsonInput ?: "")
            for (i in 1..totalSteps) {
                pb.step()
                Thread.sleep(50)
            }
            println("\n¡Análisis completado! Errores:\n$errors")
        }
    } catch (e: Exception) {
        println("Error en el análisis: ${e.message}")
    }
}
