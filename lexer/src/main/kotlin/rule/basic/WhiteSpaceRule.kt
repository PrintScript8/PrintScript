package rule.basic

import rule.TokenRule
import token.Position
import token.Token
import token.TokenInterface
import token.Whitespace

class WhiteSpaceRule : TokenRule {
    override fun match(input: String, currentIndex: Int, position: Position): TokenInterface? {
        if (currentIndex < input.length && input[currentIndex].isWhitespace()) {
            return Token(
                Whitespace,
                input[currentIndex].toString(),
                Position(position.row, position.startColumn, position.startColumn + 1)
            )
        }
        return null
    }
}
