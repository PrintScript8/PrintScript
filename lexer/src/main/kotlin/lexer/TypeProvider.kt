package org.example.lexer

import org.example.token.TokenType

class TypeProvider {

    fun getTokenType(value: String): TokenType {
        return when {
            value in listOf("let", "const", "var") -> TokenType.MODIFIER
            value in listOf("+", "-", "*", "/") -> TokenType.SUM
            value in listOf("=", ":") -> TokenType.ASSIGNATION
            value == ";" -> TokenType.ENDING
            value in listOf("String", "string", "Number", "number") -> TokenType.IDENTIFIER_TYPE
            value in listOf("println(") -> TokenType.NATIVE_METHOD
            value in listOf("(", ")") -> TokenType.PARENTHESIS_OPEN
            value.matches(Regex("""^\w+\(""")) -> TokenType.USER_METHOD
            value.matches(Regex("""^\d+$""")) -> TokenType.NUMBER_LITERAL
            value.matches(Regex("""^["'].*["']?$""")) -> TokenType.STRING_LITERAL
            else -> TokenType.IDENTIFIER_VAR
        }
    }
}