package runner

import error.Error
import formatter.FormatterInterface
import interpreter.Interpreter
import interpreter.InterpreterProvider
import lexer.Lexer
import lexer.LexerInterface
import linter.Linter
import linter.LinterProvider
import node.staticpkg.StaticNode
import parser.elements.ParserInterface
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

class Operations(private var sourceFile: String, private var version: String) {

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
    private val linter: Linter

    init {
        lexer = Lexer(lexerRules, InputStreamReader(sourceFile.byteInputStream()))
        tokenIterator = lexer.iterator()
        parser = ParserProvider(tokenIterator).getParser(version)
        interpreter = InterpreterProvider(parser.iterator()).provideInterpreter(version)
        formatter = FormatterProvider(parser.iterator()).provideFormatter(version)
        linter = LinterProvider().provideLinter("{ \"case\": \"camelCase\" , \"argument\": \"literal\" }")
        sourceFile = ""
    }

    fun validate(): List<StaticNode> {
        return this.parser.iterator().asSequence().toList()
    }

    fun execute(): List<String> {
        return interpreter.execute()
    }

    fun format(): String {
        return formatter.format()
    }

    fun analyze(): List<Error> {
        return linter.lint(parser.iterator())
    }
}
