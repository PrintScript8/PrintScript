package runner

import error.Error
import formatter.Formatter
import formatter.FormatterImpl
import interpreter.Interpreter
import interpreter.InterpreterImpl
import lexer.Lexer
import lexer.LexerImpl
import linter.LinterProvider
import node.staticpkg.StaticNode
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

    fun validate(sourceFile: String): List<StaticNode> {
        val tokens = lexer.tokenize(sourceFile)
        return parser.parse(tokens)
    }

    fun execute(sourceFile: String): List<String> {
        val tokens = lexer.tokenize(sourceFile)
        val astList = parser.parse(tokens)
        return interpreter.execute(astList)
    }

    fun format(sourceFile: String): String {
        val tokens = lexer.tokenize(sourceFile)
        val astList = parser.parse(tokens)
        return formatter.execute(astList)
    }

    fun analyze(sourceFile: String): List<Error> {
        val linter = LinterProvider().provideLinter("{ \"case\": \"camelCase\" , \"argument\": \"literal\" }")
        val tokens = lexer.tokenize(sourceFile)
        val astList = parser.parse(tokens)
        return linter.lint(astList)
    }
}
