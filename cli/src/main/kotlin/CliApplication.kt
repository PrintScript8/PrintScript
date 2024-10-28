package cli

import java.util.Scanner

class CliApplication(private val fileManager: FileManager, private val executor: OperationExecutor) {
    private val scanner = Scanner(System.`in`)
    private var continueOperations = true

    fun run() {
        while (continueOperations) {
            showMenu()
            val action = readAction() ?: continue

            if (!continueOperations) break

            val inputFile = fileManager.getInputFile(scanner)
            val jsonInput = if (action.requiresJson()) readJson(action) else null

            // Ejecutar la acción
            executor.execute(action, inputFile, jsonInput)

            askContinue()
        }
    }

    private fun showMenu() {
        println("Seleccione una acción:")
        println("1 -> Validate")
        println("2 -> Format")
        println("3 -> Execute")
        println("4 -> Analyze")
        println("5 -> Salir")
    }

    private fun readAction(): Action? {
        return when (scanner.nextInt()) {
            1 -> Action.VALIDATE
            2 -> Action.FORMAT
            3 -> Action.EXECUTE
            4 -> Action.ANALYZE
            5 -> {
                println("Saliendo...")
                continueOperations = false
                null
            }
            else -> {
                println("Opción no válida. Intente nuevamente...")
                null
            }
        }
    }

    private fun readJson(action: Action): String? {
        println("Ingrese el JSON para $action:")
        scanner.nextLine() // Limpiar el buffer
        return scanner.nextLine()
    }

    private fun askContinue() {
        println("¿Desea realizar otra operación? (s/n):")
        continueOperations = scanner.next().equals("s", ignoreCase = true)
    }
}
