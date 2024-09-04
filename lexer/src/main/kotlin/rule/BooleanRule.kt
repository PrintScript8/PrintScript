package rule

import token.Bool
import token.Position
import token.Token
import token.TypeId

class BooleanRule : TokenRule {

    private val keywords = mapOf(
        "boolean" to TypeId,
        "true" to Bool,
        "false" to Bool
    )

    override fun match(input: String, position: Position): Token? {
        for ((keyword, tokenType) in keywords) {
            if (input.startsWith(keyword)) {
                return Token(
                    tokenType, keyword,
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
