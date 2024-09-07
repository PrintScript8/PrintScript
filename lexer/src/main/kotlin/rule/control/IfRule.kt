package rule.control

import rule.TokenRule
import token.If
import token.Position
import token.Token
import token.TokenInterface

class IfRule : TokenRule {
    override fun match(input: String, position: Position): TokenInterface? {
        return if (input.startsWith("if") && isStandaloneKeyword(input)) {
            Token(If, "if", Position(position.row, position.startColumn, position.startColumn + 1))
        } else {
            null
        }
    }

    private fun isStandaloneKeyword(input: String): Boolean {
        return input.length == 2 || input[2].isWhitespace()
    }
}
