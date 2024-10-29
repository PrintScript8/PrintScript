package utils

import me.tongfei.progressbar.ProgressBar
import runner.Operations
import java.io.FileInputStream

class OperationExecutor {

    companion object {
        private const val TOTAL_STEPS = 100
        private const val SLEEP_TIME = 50
    }

    fun execute(action: Action, inputFile: String, jsonInput: String?) {
        val version = "1.1"
        val inputStream = FileInputStream(inputFile)
        val operations = Operations(inputStream, version)

        when (action) {
            Action.VALIDATE -> validateCode(operations)
            Action.FORMAT -> formatCode(operations, jsonInput)
            Action.EXECUTE -> executeCode(operations)
            Action.ANALYZE -> analyzeCode(operations, jsonInput)
        }
    }

    private fun validateCode(operations: Operations) {
        try {
            println("Validando código...")
            ProgressBar("Validating", TOTAL_STEPS.toLong()).use { pb ->
                operations.validate()
                for (step in 1..TOTAL_STEPS) {
                    pb.step()
                    Thread.sleep(SLEEP_TIME.toLong())
                }
                println("\n¡Validación completada!")
            }
        } catch (e: IllegalArgumentException) {
            println("Error en la validación: ${e.message}")
        }
    }

    private fun formatCode(operations: Operations, jsonInput: String?) {
        try {
            println("Formateando código...")
            ProgressBar("Formatting", TOTAL_STEPS.toLong()).use { pb ->
                val formattedCode = operations.format(jsonInput ?: "")
                for (step in 1..TOTAL_STEPS) {
                    pb.step()
                    Thread.sleep(SLEEP_TIME.toLong())
                }
                println("\nCódigo formateado:\n$formattedCode")
            }
        } catch (e: IllegalArgumentException) {
            println("Error en el formateo: ${e.message}")
        }
    }

    private fun executeCode(operations: Operations) {
        try {
            println("Ejecutando código...")
            ProgressBar("Executing", TOTAL_STEPS.toLong()).use { pb ->
                val output = operations.execute().asSequence().toList()
                for (step in 1..TOTAL_STEPS) {
                    pb.step()
                    Thread.sleep(SLEEP_TIME.toLong())
                }
                println("\nSalida de la ejecución:\n$output")
            }
        } catch (e: IllegalArgumentException) {
            println("Error en la ejecución: ${e.message}")
        }
    }

    private fun analyzeCode(operations: Operations, jsonInput: String?) {
        try {
            println("Analizando código...")
            ProgressBar("Analyzing", TOTAL_STEPS.toLong()).use { pb ->
                val errors = operations.analyze(jsonInput ?: "")
                for (step in 1..TOTAL_STEPS) {
                    pb.step()
                    Thread.sleep(SLEEP_TIME.toLong())
                }
                println("\n¡Análisis completado! Errores:\n$errors")
            }
        } catch (e: IllegalArgumentException) {
            println("Error en el análisis: ${e.message}")
        }
    }
}
