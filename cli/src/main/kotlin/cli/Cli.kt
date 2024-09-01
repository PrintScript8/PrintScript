package cli

import kotlinx.cli.ArgParser
import kotlinx.cli.ArgType
import kotlinx.cli.default
import operation.Operation
import operation.Operations
import java.io.FileInputStream

fun main(args: Array<String>) {
    val cli = Cli()
    cli.run(args)
}

class Cli {
    fun run(args: Array<String>): String {
        val parser = ArgParser("CLI-Tool")

        val operation by
        parser.option(ArgType.Choice<Operation>(), shortName = "o", description = "Operation to perform")
        val file by parser.argument(ArgType.String, description = "Source file")
        val version by
        parser.option(ArgType.String, shortName = "v", description = "Version of the file").default("1.0")
        val configFile by
        parser.option(ArgType.String, shortName = "c", description = "Configuration file for formatting")

        parser.parse(args)
        val sourceFile = FileInputStream(file)
        val operations = Operations()

        return when (operation) {
            Operation.Validation -> operations.validate(sourceFile)
            Operation.Execution -> operations.execute(sourceFile, version)
            Operation.Formatting -> operations.format(sourceFile, configFile)
            Operation.Analyzing -> operations.analyze(sourceFile)
            null -> throw IllegalArgumentException("No operation provided")
        }
    }
}
