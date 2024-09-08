package rule.expression

import rule.TokenRule
import token.Assignment
import token.Position
import token.Token
import token.TokenInterface

class AssignationRule : TokenRule {
    override fun match(input: String, currentIndex: Int, position: Position): TokenInterface? {
        if (input[currentIndex] != '=') {
            return null
        }
        return Token(Assignment, "=", Position(position.row, position.startColumn, position.startColumn))
    }
}
