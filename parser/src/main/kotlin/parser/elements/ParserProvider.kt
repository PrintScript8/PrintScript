package parser.elements

import node.PrimType
import parser.strategies.AssignationStrategy
import parser.strategies.BooleanStrategy
import parser.strategies.DeclarationStrategy
import parser.strategies.MethodStrategy
import parser.strategies.ModifierStrategy
import parser.strategies.ParseStrategy
import token.Assignment
import token.BooleanLiteral
import token.CloseParenthesis
import token.Declaration
import token.Divide
import token.Ending
import token.Identifier
import token.Minus
import token.Modifier
import token.Multiply
import token.NativeMethod
import token.NumberLiteral
import token.OpenParenthesis
import token.Plus
import token.StringLiteral
import token.TokenType
import token.TypeId
import token.Whitespace

class ParserProvider {

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

    private val tokenTypes2 = originalTokenTypes + BooleanLiteral

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
        BooleanLiteral to BooleanStrategy()
    )

    private val parsers: Map<String, Parser> = mapOf(
        "1.0" to Parser2(TokenHandler(ogStrategy)),
        "1.1" to Parser2(TokenHandler(strategy2))
    )

    fun getParser(version: String): Parser {
        return parsers[version] ?: throw IllegalArgumentException("Version not found")
    }
}
