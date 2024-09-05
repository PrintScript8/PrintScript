package rule.inherent

import rule.TokenRule
import token.NativeMethod
import token.Position
import token.Token
import token.TokenInterface

abstract class BaseNativeMethodRule(private val keyword: String) : TokenRule {
    override fun match(input: String, position: Position): TokenInterface? {
        if (input.startsWith(keyword)) {
            val nextCharIndex = keyword.length
            if (nextCharIndex < input.length && input[nextCharIndex] == '(') {
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
