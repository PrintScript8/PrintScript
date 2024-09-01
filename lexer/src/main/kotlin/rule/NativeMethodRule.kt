package rule

import token.NativeMethod
import token.Position
import token.Token
import token.TokenInterface

class NativeMethodRule : TokenRule {
    override fun match(input: String, position: Position): TokenInterface? {
        val nativeMethodKeywords = listOf("println(")
        for (keyword in nativeMethodKeywords) {
            if (input.startsWith(keyword)) {
                return Token(
                    NativeMethod, keyword,
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
