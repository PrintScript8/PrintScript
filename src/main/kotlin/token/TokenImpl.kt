package org.example.token

class TokenImpl(
    private val type: TokenType,
    private val representation: String,
    private val line: Int
) : Token {

    override fun getType(): TokenType {
        return type
    }

    override fun getString(): String {
        return representation
    }

    override fun getLine(): Int {
        return line
    }

    override fun toString(): String {
        return "$type | $representation | $line"
    }
}