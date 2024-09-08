package rule.literal

import rule.TokenRule
import token.NumberLiteral
import token.Position
import token.Token
import token.TokenInterface

class NumberLiteralRule : TokenRule {
    override fun match(input: String, currentIndex: Int, position: Position): TokenInterface? {
        if (shouldReturnNull(input, currentIndex)) return null

        val index = findEndIndex(input, currentIndex)
        return if (isInvalidToken(input, currentIndex, index)) {
            null
        } else {
            createToken(input, currentIndex, index, position)
        }
    }

    private fun shouldReturnNull(input: String, currentIndex: Int): Boolean {
        return currentIndex >= input.length || !input[currentIndex].isDigit()
    }

    private fun findEndIndex(input: String, currentIndex: Int): Int {
        var index = currentIndex
        var hasDot = false
        while (index < input.length && (input[index].isDigit() || input[index] == '.')) {
            if (input[index] == '.' && hasDot) break
            hasDot = hasDot || input[index] == '.'
            index++
        }
        return index
    }

    private fun isInvalidToken(input: String, currentIndex: Int, index: Int): Boolean {
        return index <= currentIndex || input[index - 1] == '.'
    }

    private fun createToken(input: String, currentIndex: Int, index: Int, position: Position): TokenInterface {
        val tokenText = input.substring(currentIndex, index)
        val endColumn = position.startColumn + tokenText.length - 1
        return Token(NumberLiteral, tokenText, Position(position.row, position.startColumn, endColumn))
    }
}
