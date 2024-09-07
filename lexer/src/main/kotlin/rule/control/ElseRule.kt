package rule.control

import rule.TokenRule
import token.Else
import token.Position
import token.Token
import token.TokenInterface

class ElseRule : TokenRule {
    companion object {
        private const val KEYWORD_LENGTH = 4
    }

    override fun match(input: String, position: Position): TokenInterface? {
        return if (input.startsWith("else") && isStandaloneKeyword(input)) {
            Token(Else, "else", Position(position.row, position.startColumn, position.startColumn + KEYWORD_LENGTH - 1))
        } else {
            null
        }
    }

    private fun isStandaloneKeyword(input: String): Boolean {
        return input.length == KEYWORD_LENGTH || input[KEYWORD_LENGTH].isWhitespace()
    }
}
