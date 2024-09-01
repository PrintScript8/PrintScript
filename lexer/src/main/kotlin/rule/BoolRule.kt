package rule

import token.Bool
import token.Position
import token.Token
import token.TokenInterface

class BoolRule : TokenRule {
    override fun match(input: String, position: Position): TokenInterface? {
        val boolKeywords = listOf("true", "false")
        for (keyword in boolKeywords) {
            if (input.startsWith(keyword)) {
                return Token(
                    Bool, keyword,
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
}
