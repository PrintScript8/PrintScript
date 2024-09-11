package cli

import kotlinx.cli.ArgParser
import kotlinx.cli.ArgType
import kotlinx.cli.default
import operation.Operation
import java.util.LinkedList
import java.util.Queue

fun main(args: Array<String>) {
    val cli = Cli(LinkedList())
    cli.run(args)
}

class Cli(private val inputQueue: Queue<String>) {

    fun run(args: Array<String>): String {
        val parser = ArgParser("CLI-Tool")

        val operation by
        parser.option(ArgType.Choice<Operation>(), shortName = "o", description = "Operation to perform")
        val sourceFile by parser.argument(ArgType.String, description = "Source file")
        val version by
        parser.option(ArgType.String, shortName = "v", description = "Version of the file").default("1.0")
        val configFile by
        parser.option(ArgType.String, shortName = "c", description = "Configuration file for formatting")

        parser.parse(args)
        val operations = OutputRunner()

        return when (operation) {
            Operation.Validation -> operations.validate(sourceFile, inputQueue)
            Operation.Execution -> operations.execute(sourceFile, version, inputQueue)
            Operation.Formatting -> operations.format(sourceFile, configFile, inputQueue)
            Operation.Analyzing -> operations.analyze(sourceFile, inputQueue)
            null -> throw IllegalArgumentException("No operation provided")
        }
    }
}
