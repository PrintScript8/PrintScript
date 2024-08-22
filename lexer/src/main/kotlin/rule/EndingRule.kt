package rule

import token.Ending
import token.Position
import token.Token
import token.TokenImpl

// Implementa una regla para identificar el token ";"
class EndingRule : TokenRule {
    override fun match(input: String, position: Position): Token? {
        if (input.startsWith(";")) {
            return TokenImpl(Ending, ";", Position(position.row, position.startColumn, position.startColumn))
        }
        return null
    }
}
