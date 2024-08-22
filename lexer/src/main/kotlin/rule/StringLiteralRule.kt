package rule

import token.StringLiteral
import lexer.Token
import lexer.TokenImpl
import token.Position

// Implementa una regla para identificar literales de cadena
class StringLiteralRule : TokenRule {
    override fun match(input: String, position: Position): Token? {
        val regex = Regex("^\"(.*?)\"") // Asegúrate de que el regex captura las cadenas entre comillas
        val matchResult = regex.find(input) ?: return null
        val tokenText = matchResult.value
        val endColumn = position.startColumn + tokenText.length - 1
        return TokenImpl(StringLiteral, tokenText, Position(position.row, position.startColumn, endColumn))
    }
}
