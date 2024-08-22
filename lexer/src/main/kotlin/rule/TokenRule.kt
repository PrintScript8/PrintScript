package rule

import lexer.Token
import token.Position

// Define la interfaz TokenRule
interface TokenRule {
    fun match(input: String, position: Position): Token?
}
