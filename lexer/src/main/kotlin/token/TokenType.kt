package token

// Define la sealed class TokenType
sealed class TokenType(val name: String)

object Identifier : TokenType("IDENTIFIER")
object NumberLiteral : TokenType("NUMBER_LITERAL")
object StringLiteral : TokenType("STRING_LITERAL")
object Plus : TokenType("PLUS")
object Minus : TokenType("MINUS")
object Multiply : TokenType("MULTIPLY")
object Divide : TokenType("DIVIDE")
object Whitespace : TokenType("WHITESPACE")
object Declaration : TokenType("DECLARATION")
object Assignment : TokenType("ASSIGNMENT")
object Ending : TokenType("ENDING")
