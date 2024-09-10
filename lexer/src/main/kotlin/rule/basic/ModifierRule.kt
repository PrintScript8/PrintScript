package rule.basic

import rule.TokenRule
import token.Modifier
import token.Position
import token.Token
import token.TokenInterface

abstract class ModifierRule(private val keyword: String) : TokenRule {
    override fun match(input: String, currentIndex: Int, position: Position): TokenInterface? {
        for (i in keyword.indices) {
            if (currentIndex + i >= input.length || input[currentIndex + i] != keyword[i]) {
                return null
            }
        }
        return Token(
            Modifier, keyword,
            Position(
                position.row,
                position.startColumn,
                position.startColumn + keyword.length - 1
            )
        )
    }
}
