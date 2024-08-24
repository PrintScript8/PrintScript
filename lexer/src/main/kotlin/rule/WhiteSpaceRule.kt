package rule

import token.Position
import token.Token
import token.TokenImpl
import token.Whitespace

class WhiteSpaceRule : TokenRule {
    override fun match(input: String, position: Position): Token? {
        val regex = Regex("^\\s+")
        val matchResult = regex.find(input) ?: return null
        val tokenText = matchResult.value
        val length = tokenText.length
        return TokenImpl(
            Whitespace,
            tokenText,
            Position(position.row, position.startColumn, position.startColumn + length - 1)
        )
    }
}
