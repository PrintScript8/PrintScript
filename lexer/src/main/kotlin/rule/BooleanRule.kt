package rule

import token.BooleanLiteral
import token.Position
import token.Token
import token.TokenImpl
import token.TypeId

class BooleanRule : TokenRule {

    private val keywords = mapOf(
        "boolean" to TypeId,
        "true" to BooleanLiteral,
        "false" to BooleanLiteral
    )

    override fun match(input: String, position: Position): Token? {
        for ((keyword, tokenType) in keywords) {
            if (input.startsWith(keyword)) {
                return TokenImpl(
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
