package rule

import token.Identifier
import token.Position
import token.Token
import token.TokenImpl

// Implementa una regla para identificar identificadores
class IdentifierRule : TokenRule {
    override fun match(input: String, position: Position): Token? {
        val regex = Regex("^[a-zA-Z_][a-zA-Z0-9_]*")
        val matchResult = regex.find(input) ?: return null
        val tokenText = matchResult.value
        val endColumn = position.startColumn + tokenText.length - 1
        return TokenImpl(Identifier, tokenText, Position(position.row, position.startColumn, endColumn))
    }
}
