package rule

import token.NumberLiteral
import token.Position
import token.Token
import token.TokenImpl

// Implementa una regla para identificar números
class NumberLiteralRule : TokenRule {
    override fun match(input: String, position: Position): Token? {
        val regex = Regex("^\\d+(\\.\\d+)?")
        val matchResult = regex.find(input) ?: return null
        val tokenText = matchResult.value
        val endColumn = position.startColumn + tokenText.length - 1
        return TokenImpl(NumberLiteral, tokenText, Position(position.row, position.startColumn, endColumn))
    }
}
