package app

import utils.FileManager
import utils.OperationExecutor

fun main() {
    val fileManager = FileManager()
    val executor = OperationExecutor()
    val cliApp = CliApplication(fileManager, executor)
    cliApp.run()
}
