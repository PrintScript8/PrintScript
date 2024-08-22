package token

// Implementa la clase ConcreteToken que usa la interfaz Token
data class TokenImpl(
    override val type: TokenType,
    override val text: String,
    override val position: Position
) : Token {
    override fun toString(): String {
        return "${type.name} " +
            "| \"$text\" " +
            "| row = ${position.row} " +
            "| [${position.startColumn}, ${position.endColumn}] columns"
    }
}
