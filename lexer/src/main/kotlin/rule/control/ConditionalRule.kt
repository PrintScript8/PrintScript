package rule.control

import rule.TokenRule
import token.Else
import token.If
import token.Position
import token.Token
import token.TokenInterface

abstract class ConditionalRule(private val keyword: String) : TokenRule {
    override fun match(input: String, currentIndex: Int, position: Position): TokenInterface? {
        val lastIndex = currentIndex + keyword.length
        var token: TokenInterface? = null

        if (lastIndex <= input.length) {
            var match = true
            for (i in keyword.indices) {
                if (input[currentIndex + i] != keyword[i]) {
                    match = false
                    break
                }
            }
            val validSyntax = lastIndex == input.length || input[lastIndex].isWhitespace() || input[lastIndex] == '('
            if (match && validSyntax) {
                val tokenType = if (keyword == "if") If else Else
                token = Token(
                    tokenType, keyword,
                    Position(
                        position.row,
                        position.startColumn,
                        position.startColumn + keyword.length - 1
                    )
                )
            }
        }
        return token
    }
}
