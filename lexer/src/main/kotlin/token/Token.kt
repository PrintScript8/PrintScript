package token

// Implementa la clase ConcreteToken que usa la interfaz Token
data class Token(
    override val type: TokenType,
    override val text: String,
    override val position: Position
) : TokenInterface {
    override fun toString(): String {
        return "${type.name} " +
            "| \"$text\" " +
            "| row = ${position.row} " +
            "| [${position.startColumn}, ${position.endColumn}] columns"
    }
}
