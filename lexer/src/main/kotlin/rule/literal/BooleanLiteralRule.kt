package rule.literal

import rule.TokenRule
import token.Position
import token.Token
import token.TokenInterface

class BooleanLiteralRule : TokenRule {
    override fun match(input: String, currentIndex: Int, position: Position): TokenInterface? {
        val boolKeywords = listOf("true", "false")
        for (keyword in boolKeywords) {
            if (currentIndex + keyword.length > input.length) continue

            if (isMatch(input, currentIndex, keyword)) {
                return Token(
                    token.Boolean, keyword,
                    Position(
                        position.row,
                        position.startColumn,
                        position.startColumn + keyword.length - 1
                    )
                )
            }
        }
        return null
    }

    private fun isMatch(input: String, currentIndex: Int, keyword: String): kotlin.Boolean {
        for (i in keyword.indices) {
            if (input[currentIndex + i] != keyword[i]) {
                return false
            }
        }
        return true
    }
}
