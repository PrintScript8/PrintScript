package runner

import error.Error
import formatter.FormatterInterface
import interpreter.Interpreter
import interpreter.InterpreterProvider
import lexer.Lexer
import lexer.LexerInterface
import linter.LinterProvider
import node.staticpkg.StaticNode
import parser.ParserInterface
import parser.elements.ParserProvider
import provider.FormatterProvider
import reader.InputStreamReader
import rule.basic.EndingRule
import rule.basic.IdentifierRule
import rule.basic.LetRule
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
import rule.typeid.BooleanIdRule
import rule.typeid.NumberIdRule
import rule.typeid.StringIdRule
import token.TokenInterface
import java.io.InputStream

class Operations(private var sourceFile: InputStream, private var version: String) {

    private val lexerRules = listOf(
        LetRule(),
        PrintlnRule(),
        NumberIdRule(),
        BooleanIdRule(),
        StringIdRule(),
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

    private val lexer: LexerInterface
    private val tokenIterator: Iterator<TokenInterface>
    private val parser: ParserInterface
    private val interpreter: Interpreter
    private val formatter: FormatterInterface

    init {
        lexer = Lexer(lexerRules, InputStreamReader(sourceFile))
        tokenIterator = lexer.iterator()
        parser = ParserProvider(tokenIterator).getParser(version)
        interpreter = InterpreterProvider(parser.iterator()).provideInterpreter(version)
        formatter = FormatterProvider(parser.iterator()).provideFormatter(version)
    }

    fun validate(): List<StaticNode> {
        return this.parser.iterator().asSequence().toList()
    }

    fun execute(): Iterator<String> {
        return interpreter.execute()
    }

    fun format(): String {
        return formatter.format()
    }

    fun analyze(json: String): List<Error> {
        val linter = LinterProvider().provideLinter(json, version)
        return linter.lint(parser.iterator())
    }
}
