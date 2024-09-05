package rule

import token.Assignment
import token.Position
import token.Token
import token.TokenInterface

// Implementa una regla para identificar el token "="
class AssignationRule : TokenRule {
    override fun match(input: String, position: Position): TokenInterface? {
        if (input.startsWith("=")) {
            return Token(Assignment, "=", Position(position.row, position.startColumn, position.startColumn))
        }
        return null
    }
}
