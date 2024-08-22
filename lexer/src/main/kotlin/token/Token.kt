package lexer

import token.Position
import token.TokenType

// Define la interfaz Token
interface Token {
    val type: TokenType
    val text: String
    val position: Position
}
