package cli

import runner.Operations

class OutputRunner {

    fun validate(sourceFile: String): String {
        println("Validating file...")
        val operations = Operations(sourceFile.byteInputStream(), "1.0")
        return try {
            operations.validate()
            "Validation successful"
        } catch (e: IllegalArgumentException) {
            "Error: ${e.message}"
        }
    }

    fun execute(sourceFile: String, version: String): String {
        println("Executing file with version $version...")
        val operations = Operations(sourceFile.byteInputStream(), version)
        return try {
            val result: Iterator<String> = operations.execute()
            val output = StringBuilder()
            while (result.hasNext()) {
                output.append(result.next())
                if (result.hasNext()) {
                    output.append("\n")
                }
            }
            "Result:\n$output"
        } catch (e: IllegalArgumentException) {
            "Error: ${e.message}"
        }
    }

    fun format(sourceFile: String, configFile: String?): String {
        println("Formatting file with config file $configFile...")
        val operations = Operations(sourceFile.byteInputStream(), "1.0")
        return try {
            val formatted = operations.format()
            "Formatted: $formatted"
        } catch (e: IllegalArgumentException) {
            "Error: ${e.message}"
        }
    }

    fun analyze(sourceFile: String): String {
        println("Analyzing file...")
        val operations = Operations(sourceFile.byteInputStream(), "1.0")
        return try {
            val errorList = operations.analyze("{}")
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
