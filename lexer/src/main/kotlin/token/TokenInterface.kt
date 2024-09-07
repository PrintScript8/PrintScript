package token

// Define la interfaz Token
interface TokenInterface {
    val type: TokenType
    val text: String
    val position: Position
}
