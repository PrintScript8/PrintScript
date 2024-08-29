package parser.elements

import parser.strategies.AssignationStrategy
import parser.strategies.DeclarationStrategy
import parser.strategies.MethodStrategy
import parser.strategies.ModifierStrategy
import parser.strategies.ParseStrategy
import token.Assignment
import token.Identifier
import token.Modifier
import token.NativeMethod
import token.TokenType

class ParserProvider {

    private val ogStrategy: Map<TokenType, ParseStrategy> = mapOf(
        Modifier to ModifierStrategy(),
        Identifier to DeclarationStrategy(),
        Assignment to AssignationStrategy(),
        NativeMethod to MethodStrategy()
    )

    private val parsers: Map<String, Parser> = mapOf(
        "1.0" to Parser2(TokenHandler(ogStrategy))
        // "1.1" to Parser2() This one should include the new rules for the version
    )

    fun getParser(version: String): Parser {
        return parsers[version] ?: throw IllegalArgumentException("Version not found")
    }
    // agregar nueva strategy para el 1.1
}
