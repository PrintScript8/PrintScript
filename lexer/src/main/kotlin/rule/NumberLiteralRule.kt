package rule

import token.Position
import token.NumberLiteral
import lexer.Token
import lexer.TokenImpl

// Implementa una regla para identificar números
class NumberLiteralRule : TokenRule {
    override fun match(input: String, position: Position): Token? {
        val regex = Regex("^\\d+")
        val matchResult = regex.find(input) ?: return null
        val tokenText = matchResult.value
        val endColumn = position.startColumn + tokenText.length - 1
        return TokenImpl(NumberLiteral, tokenText, Position(position.row, position.startColumn, endColumn))
    }
}
