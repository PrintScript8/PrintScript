package cli

import formatter.Formatter
import formatter.FormatterImpl
import interpreter.Interpreter
import interpreter.InterpreterImpl
import linter.Linter
import linter.LinterImpl
import lexer.Lexer
import lexer.LexerImpl
import rule.StringLiteralRule
import rule.IdentifierRule
import rule.NumberLiteralRule
import rule.OperationRule
import rule.PlusOperation
import rule.MinusOperation
import rule.MultiplyOperation
import rule.DivideOperation

class Operations {

    private val lexerRules = listOf(
        StringLiteralRule(),
        IdentifierRule(),
        NumberLiteralRule(),
        OperationRule(listOf(PlusOperation, MinusOperation, MultiplyOperation, DivideOperation))
    )

    private val lexer: Lexer = LexerImpl(lexerRules)
    private val interpreter: Interpreter = InterpreterImpl()
    private val linter: Linter = LinterImpl()
    private val formatter: Formatter = FormatterImpl()

    fun validate(sourceFile: String) {
        println("Validating $sourceFile...")
        try {
            val tokens = lexer.tokenize(sourceFile)
            //use parser
        }
        catch (e: Exception) {
            println("Error: ${e.message}")
        }
    }

    fun execute(sourceFile: String, version: String) {
        println("Executing $sourceFile with version $version...")
        try {
            val tokens = lexer.tokenize(sourceFile)
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
            val tokens = lexer.tokenize(sourceFile)
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
            val tokens = lexer.tokenize(sourceFile)
            //use parser
            //use linter with parser result
        }
        catch (e: Exception) {
            println("Error: ${e.message}")
        }
    }
}
