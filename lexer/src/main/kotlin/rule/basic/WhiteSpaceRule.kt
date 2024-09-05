package rule.basic

import rule.TokenRule
import token.Position
import token.Token
import token.TokenInterface
import token.Whitespace

class WhiteSpaceRule : TokenRule {
    override fun match(input: String, position: Position): TokenInterface? {
        val regex = Regex("^\\s+")
        val matchResult = regex.find(input) ?: return null
        val tokenText = matchResult.value
        val length = tokenText.length
        return Token(
            Whitespace,
            tokenText,
            Position(position.row, position.startColumn, position.startColumn + length - 1)
        )
    }
}
