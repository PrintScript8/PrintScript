package cli

import runner.Operations

class OutputRunner {
    private val operations: Operations = Operations()

    fun validate(sourceFile: String): String {
        println("Validating file...")
        return try {
            operations.validate(sourceFile)
            "Validation successful"
        } catch (e: IllegalArgumentException) {
            "Error: ${e.message}"
        }
    }

    fun execute(sourceFile: String, version: String): String {
        println("Executing file with version $version...")
        return try {
            val result: List<String> = operations.execute(sourceFile)
            "Result:\n${result.joinToString(separator = "\n")}"
        } catch (e: IllegalArgumentException) {
            "Error: ${e.message}"
        }
    }

    fun format(sourceFile: String, configFile: String?): String {
        println("Formatting file with config file $configFile...")
        return try {
            val formatted = operations.format(sourceFile)
            "Formatted: $formatted"
        } catch (e: IllegalArgumentException) {
            "Error: ${e.message}"
        }
    }

    fun analyze(sourceFile: String): String {
        println("Analyzing file...")
        return try {
            val errorList = operations.analyze(sourceFile)
            if (errorList.isEmpty()) {
                "No errors found"
            } else {
                "Errors found: $errorList"
            }
        } catch (e: IllegalArgumentException) {
            "Error: ${e.message}"
        }
    }
}
