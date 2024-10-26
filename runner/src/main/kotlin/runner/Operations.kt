package runner

import error.Error
import inputreader.InputQueueService
import interpreter.Interpreter
import interpreter.InterpreterProvider
import lexer.LexerInterface
import linter.LinterProvider
import node.staticpkg.StaticNode
import parser.ParserInterface
import parser.elements.ParserProvider
import provider.FormatterProvider
import provider.LexerProvider
import reader.InputStreamReader
import token.TokenInterface
import java.io.InputStream

class Operations(sourceFile: InputStream, private var version: String, provider: Iterator<String>? = null) {

    constructor(sourceFile: InputStream, version: String) : this(sourceFile, version, null)

    private val lexer: LexerInterface
    private val tokenIterator: Iterator<TokenInterface>
    private val parser: ParserInterface
    private val interpreter: Interpreter

    init {
        lexer = LexerProvider(InputStreamReader(sourceFile)).getLexer(version)
        tokenIterator = lexer.iterator()
        parser = ParserProvider(tokenIterator).getParser(version)
        interpreter = InterpreterProvider(parser.iterator()).provideInterpreter(version)
        provider?.let { InputQueueService.initialize(it) }
    }

    fun validate(): List<StaticNode> {
        return this.parser.iterator().asSequence().toList()
    }

    fun execute(): Iterator<String> {
        return interpreter.iterator()
    }

    fun format(json: String): String {
        val formatter = FormatterProvider().provideFormatter(json, version)
        return formatter.format(parser.iterator())
    }

    fun analyze(json: String): List<Error> {
        val linter = LinterProvider().provideLinter(json, version)
        return linter.lint(parser.iterator())
    }
}
