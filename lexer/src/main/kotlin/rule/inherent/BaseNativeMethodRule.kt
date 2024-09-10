package rule.inherent

import rule.TokenRule
import token.NativeMethod
import token.Position
import token.Token
import token.TokenInterface

abstract class BaseNativeMethodRule(private val keyword: String) : TokenRule {
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
            if (match && isValidNextChar(input, lastIndex)) {
                token = Token(
                    NativeMethod, keyword,
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

    private fun isValidNextChar(input: String, lastIndex: Int): Boolean {
        return lastIndex == input.length || input[lastIndex] == ' ' || input[lastIndex] == '('
    }
}
