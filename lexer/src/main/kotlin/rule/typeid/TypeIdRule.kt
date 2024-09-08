package rule.typeid

import rule.TokenRule
import token.Position
import token.Token
import token.TokenInterface
import token.TypeId

abstract class TypeIdRule(private val keyword: String) : TokenRule {
    override fun match(input: String, currentIndex: Int, position: Position): TokenInterface? {
        var token: TokenInterface? = null
        val lastIndex = currentIndex + keyword.length

        if (lastIndex <= input.length) {
            var match = true
            for (i in keyword.indices) {
                if (input[currentIndex + i] != keyword[i]) {
                    match = false
                    break
                }
            }
            if (match && (lastIndex == input.length || input[lastIndex].isWhitespace())) {
                token = Token(
                    TypeId, keyword,
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
