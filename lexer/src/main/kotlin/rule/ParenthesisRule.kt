package rule

import token.CloseParenthesis
import token.OpenParenthesis
import token.Position
import token.Token
import token.TokenInterface
import token.TokenType

interface Parenthesis {
    val symbol: String
    val tokenType: TokenType
}

object OpenParenthesisRule : Parenthesis {
    override val symbol = "("
    override val tokenType = OpenParenthesis
}

object CloseParenthesisRule : Parenthesis {
    override val symbol = ")"
    override val tokenType = CloseParenthesis
}

class ParenthesisRule(private val parenthesisRules: List<Parenthesis>) : TokenRule {

    override fun match(input: String, position: Position): TokenInterface? {
        for (parenthesisRule in parenthesisRules) {
            if (input.startsWith(parenthesisRule.symbol)) {
                return Token(
                    parenthesisRule.tokenType,
                    parenthesisRule.symbol,
                    Position(position.row, position.startColumn, position.startColumn)
                )
            }
        }
        return null
    }
}
