package rule.basic

import rule.TokenRule
import token.Ending
import token.Position
import token.Token
import token.TokenInterface

class EndingRule : TokenRule {
    override fun match(input: String, currentIndex: Int, position: Position): TokenInterface? {
        if (input[currentIndex] != ';') {
            return null
        }
        return Token(Ending, ";", Position(position.row, position.startColumn, position.startColumn))
    }
}
