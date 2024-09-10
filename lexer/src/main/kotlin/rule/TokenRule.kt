package rule

import token.Position
import token.TokenInterface

interface TokenRule {
    fun match(input: String, currentIndex: Int, position: Position): TokenInterface?
}
