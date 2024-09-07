package runner

import error.Error
import formatter.Formatter
import formatter.FormatterImpl
import interpreter.Interpreter
import interpreter.IntepreterProvider
import lexer.Lexer
import lexer.LexerImpl
import linter.LinterProvider
import node.staticpkg.StaticNode
import parser.elements.ParserInterface
import parser.elements.ParserProvider
import rule.basic.ModifierRule
import rule.literal.NumberLiteralRule
import rule.literal.StringLiteralRule
import rule.operation.OperationRule
import rule.expression.AssignationRule
import rule.expression.DeclarationRule
import rule.operation.PlusOperation
import rule.operation.MinusOperation
import rule.operation.DivideOperation
import rule.operation.MultiplyOperation
import rule.basic.EndingRule
import rule.control.OpenParenthesisRule
import rule.control.ParenthesisRule
import rule.basic.IdentifierRule
import rule.basic.TypeIdRule
import rule.basic.WhiteSpaceRule
import rule.control.CloseParenthesisRule
import rule.inherent.PrintlnRule

class Operations(sourceFile: String, version: String) {

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

    private val lexer: Lexer = LexerImpl(lexerRules)
    private val tokenIterator = lexer.iterator(sourceFile)
    private val parser: ParserInterface = ParserProvider(tokenIterator).getParser(version)
    private val interpreter: Interpreter = IntepreterProvider(parser.iterator()).provideInterpreter(version)
    private val formatter: Formatter = FormatterImpl()

    fun validate(): List<StaticNode> {
        return parser.parse()
    }

    fun execute(): List<String> {
        return interpreter.execute()
    }

    fun format(): String {
        return formatter.execute(parser.parse())
    }

    fun analyze(): List<Error> {
        val linter = LinterProvider().provideLinter("{ \"case\": \"camelCase\" , \"argument\": \"literal\" }")
        return linter.lint(parser.parse())
    }
}

