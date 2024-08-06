package org.example.lexer

import org.example.token.TokenType

class TypeProvider {

    private val typesMap: Map<String, TokenType> = mapOf(
        "let" to TokenType.KEYWORD,
        "cast" to TokenType.KEYWORD,
        "var" to TokenType.KEYWORD,
        "+" to TokenType.OPERAND,
        "-" to TokenType.OPERAND,
        "*" to TokenType.OPERAND,
        "/" to TokenType.OPERAND,
        "=" to TokenType.ASSIGNATION,
        ";" to TokenType.ENDING,
        "String" to TokenType.LITERAL_TYPE,
        "Number" to TokenType.LITERAL_TYPE
        //falta chequear por valores reales de string o numero
    )

    fun getTokenType(value: String): TokenType{
        val type: TokenType? = typesMap.get(value)
        if(type != null){
            return type;
        }
        else{
            //This should check obsecure types for
            return TokenType.IDENTIFIER_VAR
        }
    }
}