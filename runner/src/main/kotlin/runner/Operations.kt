package runner

import error.Error
import formatter.Formatter
import formatter.FormatterImpl
import interpreter.Interpreter
import interpreter.InterpreterImpl
import lexer.Lexer
import linter.LinterProvider
import node.staticpkg.StaticNode
import parser.elements.Parser
import parser.elements.ParserProvider
import rule.basic.EndingRule
import rule.basic.IdentifierRule
import rule.basic.ModifierRule
import rule.basic.TypeIdRule
import rule.basic.WhiteSpaceRule
import rule.control.CloseParenthesisRule
import rule.control.OpenParenthesisRule
import rule.control.ParenthesisRule
import rule.expression.AssignationRule
import rule.expression.DeclarationRule
import rule.inherent.PrintlnRule
import rule.literal.NumberLiteralRule
import rule.literal.StringLiteralRule
import rule.operation.DivideOperation
import rule.operation.MinusOperation
import rule.operation.MultiplyOperation
import rule.operation.OperationRule
import rule.operation.PlusOperation

class Operations {

    private val parserProvider: ParserProvider = ParserProvider()

    private val lexerRules = listOf(
        ModifierRule(),
        PrintlnRule(),
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

    private val lexer: Lexer = Lexer(lexerRules)
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
