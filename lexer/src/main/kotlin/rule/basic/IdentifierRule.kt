package rule.basic

import rule.TokenRule
import token.Identifier
import token.Position
import token.Token
import token.TokenInterface

class IdentifierRule : TokenRule {
    override fun match(input: String, currentIndex: Int, position: Position): TokenInterface? {
        if (currentIndex >= input.length || !input[currentIndex].isLetter() && input[currentIndex] != '_') return null

        var index = currentIndex
        while (index < input.length && (input[index].isLetterOrDigit() || input[index] == '_')) {
            index++
        }

        val tokenText = input.substring(currentIndex, index)
        val endColumn = position.startColumn + tokenText.length - 1
        return Token(Identifier, tokenText, Position(position.row, position.startColumn, endColumn))
    }
}
