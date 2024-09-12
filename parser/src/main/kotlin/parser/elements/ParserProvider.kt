package parser.elements

import node.PrimType
import parser.Parser
import parser.ParserInterface
import parser.strategies.*
import token.Assignment
import token.Boolean
import token.CloseBrace
import token.CloseParenthesis
import token.Declaration
import token.Divide
import token.Else
import token.Ending
import token.Identifier
import token.If
import token.Minus
import token.Modifier
import token.Multiply
import token.NativeMethod
import token.NumberLiteral
import token.OpenBrace
import token.OpenParenthesis
import token.Plus
import token.StringLiteral
import token.TokenInterface
import token.TokenType
import token.TypeId
import token.Whitespace

class ParserProvider(private val iterator: Iterator<TokenInterface>) {

    private val originalTokenTypes: Set<TokenType> = setOf(
        Identifier,
        NumberLiteral,
        StringLiteral,
        Plus,
        Minus,
        Multiply,
        Divide,
        Whitespace,
        Declaration,
        Assignment,
        Ending,
        OpenParenthesis,
        CloseParenthesis,
        TypeId,
        NativeMethod,
        Modifier
    )

    private val tokenTypes2: Set<TokenType> = originalTokenTypes + OpenBrace + CloseBrace + If + Else + Boolean

    private val methodStrategy = MethodStrategy(originalTokenTypes, setOf("println"))
    private val ogStrategy: Map<TokenType, ParseStrategy> = mapOf(
        Modifier to ModifierStrategy(setOf("let")),
        Identifier to DeclarationStrategy(arrayOf(PrimType.STRING, PrimType.NUMBER)),
        NativeMethod to methodStrategy,
        Assignment to AssignationStrategy(originalTokenTypes, methodStrategy)
    )

    private val methodStrategy2 = MethodStrategy(tokenTypes2, setOf("println", "readEnv", "readInput"))
    private val strategy2: Map<TokenType, ParseStrategy> = mapOf(
        Modifier to ModifierStrategy(setOf("let", "const")),
        Identifier to DeclarationStrategy(arrayOf(PrimType.STRING, PrimType.NUMBER, PrimType.BOOLEAN)),
        NativeMethod to methodStrategy2,
        Assignment to AssignationStrategy(tokenTypes2, methodStrategy2),
        If to IfStrategy(),
        Else to ElseStrategy()
    )

    private val parsers: Map<String, ParserInterface> = mapOf(
        "1.0" to Parser(TokenHandler(ogStrategy), iterator),
        "1.1" to Parser(TokenHandler(strategy2), iterator)
    )

    fun getParser(version: String): ParserInterface {
        return parsers[version] ?: throw IllegalArgumentException("Version not found")
    }
}
