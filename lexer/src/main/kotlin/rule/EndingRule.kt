package rule

import token.Ending
import token.Position
import token.Token
import token.TokenInterface

// Implementa una regla para identificar el token ";"
class EndingRule : TokenRule {
    override fun match(input: String, position: Position): TokenInterface? {
        if (input.startsWith(";")) {
            return Token(Ending, ";", Position(position.row, position.startColumn, position.startColumn))
        }
        return null
    }
}
