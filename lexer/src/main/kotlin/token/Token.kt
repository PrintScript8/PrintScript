package token

// Define la interfaz Token
interface Token {
    val type: TokenType
    val text: String
    val position: Position
}
