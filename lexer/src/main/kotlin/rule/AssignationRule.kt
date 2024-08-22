package rule

import lexer.Token
import lexer.TokenImpl
import token.Assignment
import token.Position

// Implementa una regla para identificar el token "="
class AssignationRule : TokenRule {
    override fun match(input: String, position: Position): Token? {
        if (input.startsWith("=")) {
            return TokenImpl(Assignment, "=", Position(position.row, position.startColumn, position.startColumn))
        }
        return null
    }
}
