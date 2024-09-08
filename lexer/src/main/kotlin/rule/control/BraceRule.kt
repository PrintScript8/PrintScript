package rule.control

import rule.TokenRule
import token.CloseBrace
import token.OpenBrace
import token.Position
import token.Token
import token.TokenInterface
import token.TokenType

interface Brace {
    val symbol: Char
    val tokenType: TokenType
}

object OpenBraceRule : Brace {
    override val symbol = '{'
    override val tokenType = OpenBrace
}

object CloseBraceRule : Brace {
    override val symbol = '}'
    override val tokenType = CloseBrace
}

class BraceRule(private val braceRules: List<Brace>) : TokenRule {

    override fun match(input: String, currentIndex: Int, position: Position): TokenInterface? {
        for (braceRule in braceRules) {
            if (input[currentIndex] == (braceRule.symbol)) {
                return Token(
                    braceRule.tokenType,
                    (braceRule.symbol).toString(),
                    Position(position.row, position.startColumn, position.startColumn)
                )
            }
        }
        return null
    }
}
