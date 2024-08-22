package rule

import token.Declaration
import token.Position
import token.Token
import token.TokenImpl

// Implementa una regla para identificar el token ":"
class DeclarationRule : TokenRule {
    override fun match(input: String, position: Position): Token? {
        if (input.startsWith(":")) {
            return TokenImpl(Declaration, ":", Position(position.row, position.startColumn, position.startColumn))
        }
        return null
    }
}
