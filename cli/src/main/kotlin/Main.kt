package cli

import kotlinx.cli.ArgParser
import kotlinx.cli.ArgType
import kotlinx.cli.default
import me.tongfei.progressbar.ProgressBar
import runner.Operations
import java.io.FileInputStream

fun main(args: Array<String>) {
    val parser = ArgParser("mycompiler")

    val action by parser.option(
        ArgType.Choice(listOf("validate", "format", "execute", "analyze"), { it }),
        description = "Choose the action to perform"
    ).default("validate")

    val inputFile by parser.argument(ArgType.String, description = "Path to the input file")

    parser.parse(args)

    val version = "1.1"
    val inputStream = FileInputStream(inputFile)
    val operations = Operations(inputStream, version)

    when (action) {
        "validate" -> validateCode(operations)
        "format" -> formatCode(operations)
        "execute" -> executeCode(operations)
        "analyze" -> analyzeCode(operations)
    }
}

fun validateCode(operations: Operations) {
    try {
        println("Validating code...")
        val totalSteps = 100
        ProgressBar("Validating", totalSteps.toLong()).use { pb ->
            operations.validate()
            for (i in 1..totalSteps) {
                pb.step()
                Thread.sleep(50) // Simulate heavy task
            }
            println("\nValidation complete!")
        }
    } catch (e: Exception) {
        println("Validation failed: ${e.message}")
    }
}

fun formatCode(operations: Operations) {
    try {
        println("Formatting code...")
        val totalSteps = 100
        ProgressBar("Formatting", totalSteps.toLong()).use { pb ->
            val formattedCode = operations.format("""{
        "rules": {
            "spaceBeforeColon": false,
            "spaceAfterColon": true,
            "spaceAroundEquals": true,
            "newlineBeforePrintln": 2,
            "newlineAfterSemicolon": true
        }
    }""")
            // Pass the appropriate JSON string if needed
            for (i in 1..totalSteps) {
                pb.step()
                Thread.sleep(50) // Simulate heavy task
            }
            println("\nFormatted code: $formattedCode")
        }
    }
    catch (e: Exception) {
        println("Formatting failed: ${e.message}")
    }
}

fun executeCode(operations: Operations) {
    try {
        println("Executing code...")
        val totalSteps = 100
        ProgressBar("Executing", totalSteps.toLong()).use { pb ->
            val output = operations.execute().asSequence().toList()
            for (i in 1..totalSteps) {
                pb.step()
                Thread.sleep(50) // Simulate heavy task
            }
            println("\nExecution output: $output")
        }
    }
    catch (e: Exception) {
        println("Execution failed: ${e.message}")
    }
}

fun analyzeCode(operations: Operations) {
    try {
        println("Analyzing code...")
        val totalSteps = 100
        ProgressBar("Analyzing", totalSteps.toLong()).use { pb ->
            val errors = operations.analyze("{ \"identifier_format\": \"camel case\"}") // Pass the appropriate JSON string if needed
            for (i in 1..totalSteps) {
                pb.step()
                Thread.sleep(50) // Simulate heavy task
            }
            println("\nAnalysis complete! Errors: $errors")
        }
    }
    catch (e: Exception) {
        println("Analysis failed: ${e.message}")
    }
}
