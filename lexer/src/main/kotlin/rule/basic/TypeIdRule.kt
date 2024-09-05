package rule.basic

import rule.TokenRule
import token.Position
import token.Token
import token.TokenInterface
import token.TypeId

class TypeIdRule : TokenRule {
    override fun match(input: String, position: Position): TokenInterface? {
        val typeIdKeywords = listOf("String", "Number")
        for (keyword in typeIdKeywords) {
            if (input.startsWith(keyword)) {
                return Token(
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
