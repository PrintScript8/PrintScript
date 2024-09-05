package rule

import token.Declaration
import token.Position
import token.Token
import token.TokenInterface

// Implementa una regla para identificar el token ":"
class DeclarationRule : TokenRule {
    override fun match(input: String, position: Position): TokenInterface? {
        if (input.startsWith(":")) {
            return Token(Declaration, ":", Position(position.row, position.startColumn, position.startColumn))
        }
        return null
    }
}
