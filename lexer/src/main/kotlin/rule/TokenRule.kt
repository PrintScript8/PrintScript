package rule

import token.Position
import token.TokenInterface

interface TokenRule {
    fun match(input: String, position: Position): TokenInterface?
}
