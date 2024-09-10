package rule.expression

import rule.TokenRule
import token.Declaration
import token.Position
import token.Token
import token.TokenInterface

class DeclarationRule : TokenRule {
    override fun match(input: String, currentIndex: Int, position: Position): TokenInterface? {
        if (currentIndex >= input.length || input[currentIndex] != ':') {
            return null
        }
        return Token(Declaration, ":", Position(position.row, position.startColumn, position.startColumn))
    }
}
