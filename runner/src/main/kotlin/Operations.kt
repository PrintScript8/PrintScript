import formatter.Formatter
import formatter.FormatterImpl
import interpreter.Interpreter
import interpreter.InterpreterImpl
import lexer.Lexer
import lexer.LexerImpl
import linter.LinterProvider
import parser.elements.Parser
import parser.elements.ParserProvider
import rule.AssignationRule
import rule.CloseParenthesisRule
import rule.DeclarationRule
import rule.DivideOperation
import rule.EndingRule
import rule.IdentifierRule
import rule.MinusOperation
import rule.ModifierRule
import rule.MultiplyOperation
import rule.NativeMethodRule
import rule.NumberLiteralRule
import rule.OpenParenthesisRule
import rule.OperationRule
import rule.ParenthesisRule
import rule.PlusOperation
import rule.StringLiteralRule
import rule.TypeIdRule
import rule.WhiteSpaceRule

class Operations {

    private val parserProvider: ParserProvider = ParserProvider()

    private val lexerRules = listOf(
        ModifierRule(),
        NativeMethodRule(),
        TypeIdRule(),
        WhiteSpaceRule(),
        IdentifierRule(),
        NumberLiteralRule(),
        StringLiteralRule(),
        DeclarationRule(),
        AssignationRule(),
        EndingRule(),
        OperationRule(listOf(PlusOperation, MinusOperation, MultiplyOperation, DivideOperation)),
        ParenthesisRule(listOf(OpenParenthesisRule, CloseParenthesisRule))
    )

    private val lexer: Lexer = LexerImpl(lexerRules)
    private val parser: Parser = parserProvider.getParser("1.0")
    private val interpreter: Interpreter = InterpreterImpl()
    private val formatter: Formatter = FormatterImpl()

    fun validate(sourceFile: String): String {
        println("Validating file...")
        return try {
            val tokens = lexer.tokenize(sourceFile)
            parser.parse(tokens)
            "Validation successful"
        } catch (e: IllegalArgumentException) {
            "Error: ${e.message}"
        }
    }

    fun execute(sourceFile: String, version: String): String {
        println("Executing file with version $version...")
        return try {
            val tokens = lexer.tokenize(sourceFile)
            val astList = parser.parse(tokens)
            val result: List<String> = interpreter.execute(astList)
            "Result:\n${result.joinToString(separator = "\n")}"
        } catch (e: IllegalArgumentException) {
            "Error: ${e.message}"
        }
    }

    fun format(sourceFile: String, configFile: String?): String {
        println("Formatting file with config file $configFile...")
        return try {
            val tokens = lexer.tokenize(sourceFile)
            val astList = parser.parse(tokens)
            val formatted = formatter.execute(astList)
            "Formatted: $formatted"
        } catch (e: IllegalArgumentException) {
            "Error: ${e.message}"
        }
    }

    fun analyze(sourceFile: String): String {
        println("Analyzing file...")
        val linter = LinterProvider().provideLinter("{ \"case\": \"camelCase\" , \"argument\": \"literal\" }")
        return try {
            val tokens = lexer.tokenize(sourceFile)
            val astList = parser.parse(tokens)
            val errorList = linter.lint(astList)
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
