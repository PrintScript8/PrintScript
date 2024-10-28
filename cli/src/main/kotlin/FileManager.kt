package cli

import java.util.Scanner

class FileManager {
    private var inputFile: String? = null

    fun getInputFile(scanner: Scanner): String {
        if (inputFile == null || askForNewFile(scanner)) {
            println("Ingrese la ruta al archivo de entrada:")
            inputFile = scanner.next()
        }
        checkNotNull(inputFile) { "Archivo no especificado" }
        return inputFile!!
    }

    private fun askForNewFile(scanner: Scanner): Boolean {
        println("¿Desea usar el mismo archivo? (s/n):")
        val response = scanner.next()
        return response.equals("n", ignoreCase = true)
    }
}
