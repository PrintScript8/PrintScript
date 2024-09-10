package rule.literal

import rule.TokenRule
import token.Position
import token.StringLiteral
import token.Token
import token.TokenInterface

class StringLiteralRule : TokenRule {
    override fun match(input: String, currentIndex: Int, position: Position): TokenInterface? {
        var token: TokenInterface? = null

        if (currentIndex < input.length && input[currentIndex] == '"') {
            var index = currentIndex + 1
            while (index < input.length && input[index] != '"') {
                index++
            }

            if (index < input.length && input[index] == '"') {
                val tokenText = input.substring(currentIndex, index + 1)
                val endColumn = position.startColumn + tokenText.length - 1
                token = Token(StringLiteral, tokenText, Position(position.row, position.startColumn, endColumn))
            }
        }

        return token
    }
}
