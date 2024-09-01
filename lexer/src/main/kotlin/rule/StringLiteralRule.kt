package rule

import token.Position
import token.StringLiteral
import token.Token
import token.TokenInterface

// Implementa una regla para identificar literales de cadena
class StringLiteralRule : TokenRule {
    override fun match(input: String, position: Position): TokenInterface? {
        val regex = Regex("^\"(.*?)\"") // Asegúrate de que el regex captura las cadenas entre comillas
        val matchResult = regex.find(input) ?: return null
        val tokenText = matchResult.value
        val endColumn = position.startColumn + tokenText.length - 1
        return Token(StringLiteral, tokenText, Position(position.row, position.startColumn, endColumn))
    }
}
