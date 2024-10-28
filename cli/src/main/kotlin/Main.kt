package cli

fun main() {
    val fileManager = FileManager()
    val executor = OperationExecutor()
    val cliApp = CliApplication(fileManager, executor)
    cliApp.run()
}
