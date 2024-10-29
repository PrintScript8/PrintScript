package app

import utils.Action
import utils.FileManager
import utils.OperationExecutor
import java.util.Scanner

class CliApplication(private val fileManager: FileManager, private val executor: OperationExecutor) {
    private val scanner = Scanner(System.`in`)

    companion object {
        private const val VALIDATE = 1
        private const val FORMAT = 2
        private const val EXECUTE = 3
        private const val ANALYZE = 4
        private const val EXIT = 5
    }

    fun run() {
        var continueOperations = true

        while (continueOperations) {
            // Mostrar menú y leer acción
            val action = showAndReadAction()

            // Si la acción es salir, terminamos el ciclo
            if (action == Action.EXIT) {
                println("Saliendo...")
                continueOperations = false
            } else if (action != null) {
                // Obtenemos el archivo de entrada y, si es necesario, el JSON
                val inputFile = fileManager.getInputFile(scanner)
                val jsonInput = if (action.requiresJson()) readJson(action) else null

                // Ejecutar la acción
                executor.execute(action, inputFile, jsonInput)

                // Preguntar si desea continuar
                continueOperations = askContinue()
            } else {
                println("Opción no válida. Intente nuevamente...")
            }
        }
    }

    private fun showAndReadAction(): Action? {
        println("Seleccione una acción:")
        println("$VALIDATE -> Validate")
        println("$FORMAT -> Format")
        println("$EXECUTE -> Execute")
        println("$ANALYZE -> Analyze")
        println("$EXIT -> Salir")

        return when (scanner.nextInt()) {
            VALIDATE -> Action.VALIDATE
            FORMAT -> Action.FORMAT
            EXECUTE -> Action.EXECUTE
            ANALYZE -> Action.ANALYZE
            EXIT -> Action.EXIT
            else -> null
        }
    }

    private fun readJson(action: Action): String? {
        println("Ingrese el JSON para $action:")
        scanner.nextLine() // Limpiar el buffer
        return scanner.nextLine()
    }

    private fun askContinue(): Boolean {
        println("¿Desea realizar otra operación? (s/n):")
        return scanner.next().equals("s", ignoreCase = true)
    }
}
