package rule

import token.Position
import lexer.Token
import lexer.TokenImpl
import token.Whitespace

class WhitespaceRule : TokenRule {
    override fun match(input: String, position: Position): Token? {
        val regex = Regex("^\\s+")
        val matchResult = regex.find(input) ?: return null
        val tokenText = matchResult.value
        val length = tokenText.length
        return TokenImpl(Whitespace, tokenText, Position(position.row, position.startColumn, position.startColumn + length - 1))
    }
}
