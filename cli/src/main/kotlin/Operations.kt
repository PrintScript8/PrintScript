package cli.src.main.kotlin

import formatter.Formatter
import formatter.FormatterImpl
import interpreter.Interpreter
import interpreter.InterpreterImpl
import linter.Linter
import linter.LinterImpl
import org.example.lexer.Lexer
import org.example.lexer.LexerImplementation
import java.io.File

class Operations {

    private val lexer: Lexer = LexerImplementation()
    private val interpreter: Interpreter = InterpreterImpl()
    private val linter: Linter = LinterImpl()
    private val formatter: Formatter = FormatterImpl()

    fun validate(sourceFile: String) {
        println("Validating $sourceFile...")
        try {
            lexer.setFile(File(sourceFile))
            val tokens = lexer.getTokens()
            //use parser
        }
        catch (e: Exception) {
            println("Error: ${e.message}")
        }
    }

    fun execute(sourceFile: String, version: String) {
        println("Executing $sourceFile with version $version...")
        try {
            lexer.setFile(File(sourceFile))
            val tokens = lexer.getTokens()
            //use parser
            //use interpreter with parser result
        }
        catch (e: Exception) {
            println("Error: ${e.message}")
        }
    }

    fun format(sourceFile: String, configFile: String?) {
        println("Formatting $sourceFile with config file $configFile...")
        try {
            lexer.setFile(File(sourceFile))
            val tokens = lexer.getTokens()
            //use parser
            //use formatter with parser result
        }
        catch (e: Exception) {
            println("Error: ${e.message}")
        }
    }

    fun analyze(sourceFile: String) {
        println("Analyzing $sourceFile...")
        try {
            lexer.setFile(File(sourceFile))
            val tokens = lexer.getTokens()
            //use parser
            //use linter with parser result
        }
        catch (e: Exception) {
            println("Error: ${e.message}")
        }
    }
}
