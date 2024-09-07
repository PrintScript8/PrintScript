package parser.elements

import node.PrimType
import parser.strategies.AssignationStrategy
import parser.strategies.DeclarationStrategy
import parser.strategies.MethodStrategy
import parser.strategies.ModifierStrategy
import parser.strategies.ParseStrategy
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

    private val ogStrategy: Map<TokenType, ParseStrategy> = mapOf(
        Modifier to ModifierStrategy(),
        Identifier to DeclarationStrategy(arrayOf(PrimType.STRING, PrimType.NUMBER,)),
        Assignment to AssignationStrategy(originalTokenTypes),
        NativeMethod to MethodStrategy(originalTokenTypes)
    )

    private val strategy2: Map<TokenType, ParseStrategy> = mapOf(
        Modifier to ModifierStrategy(),
        Identifier to DeclarationStrategy(arrayOf(PrimType.STRING, PrimType.NUMBER, PrimType.BOOLEAN)),
        Assignment to AssignationStrategy(tokenTypes2),
        NativeMethod to MethodStrategy(tokenTypes2),
    )

    private val parsers: Map<String, ParserInterface> = mapOf(
        "1.0" to Parser(TokenHandler(ogStrategy), iterator),
        "1.1" to Parser(TokenHandler(strategy2), iterator)
    )

    fun getParser(version: String): ParserInterface {
        return parsers[version] ?: throw IllegalArgumentException("Version not found")
    }
}
