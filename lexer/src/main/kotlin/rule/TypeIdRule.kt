package rule

import token.Position
import token.Token
import token.TokenImpl
import token.TypeId

class TypeIdRule : TokenRule {
    override fun match(input: String, position: Position): Token? {
        val typeIdKeywords = listOf("String", "Number")
        for (keyword in typeIdKeywords) {
            if (input.startsWith(keyword)) {
                return TokenImpl(
                    TypeId, keyword,
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
