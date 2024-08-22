package rule

import token.Position
import token.Token

// Define la interfaz TokenRule
interface TokenRule {
    fun match(input: String, position: Position): Token?
}
