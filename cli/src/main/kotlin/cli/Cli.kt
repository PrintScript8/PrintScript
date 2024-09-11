package cli

import inputreader.InputProvider
import inputreader.QueueInputProvider
import kotlinx.cli.ArgParser
import kotlinx.cli.ArgType
import kotlinx.cli.default
import operation.Operation
import java.util.LinkedList

fun main(args: Array<String>) {
    val cli = Cli(QueueInputProvider(LinkedList()))
    cli.run(args)
}

class Cli(private val provider: InputProvider) {

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
            Operation.Validation -> operations.validate(sourceFile)
            Operation.Execution -> operations.execute(sourceFile, version, provider)
            Operation.Formatting -> operations.format(sourceFile, configFile)
            Operation.Analyzing -> operations.analyze(sourceFile)
            null -> throw IllegalArgumentException("No operation provided")
        }
    }
}
