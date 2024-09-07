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
import parser.elements.ParserInterface
import parser.elements.ParserProvider
import rule.*

class Operations(sourceFile: String) {

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
    private val tokenIterator = lexer.iterator(sourceFile)
    private val parser: ParserInterface = ParserProvider(tokenIterator).getParser("1.0")
    private val interpreter: Interpreter = InterpreterImpl(parser.iterator())
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

