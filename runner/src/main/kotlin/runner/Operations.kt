package runner

import error.Error
import formatter.FormatterInterface
import interpreter.Interpreter
import interpreter.InterpreterProvider
import lexer.LexerInterface
import linter.LinterProvider
import node.staticpkg.StaticNode
import parser.elements.ParserInterface
import parser.elements.ParserProvider
import provider.FormatterProvider
import provider.LexerProvider
import reader.InputStreamReader
import token.TokenInterface

class Operations(private var sourceFile: String, private var version: String) {

    private val lexer: LexerInterface
    private val tokenIterator: Iterator<TokenInterface>
    private val parser: ParserInterface
    private val interpreter: Interpreter
    private val formatter: FormatterInterface

    init {
        lexer = LexerProvider(InputStreamReader(sourceFile.byteInputStream())).getLexer(version)
        tokenIterator = lexer.iterator()
        parser = ParserProvider(tokenIterator).getParser(version)
        interpreter = InterpreterProvider(parser.iterator()).provideInterpreter(version)
        formatter = FormatterProvider(parser.iterator()).provideFormatter(version)

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

    fun analyze(json: String): List<Error> {
        val linter = LinterProvider().provideLinter(json, version)
        return linter.lint(parser.iterator())
    }
}
