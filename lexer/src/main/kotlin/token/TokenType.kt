package token

// Define la sealed class TokenType
sealed class TokenType(val name: String)

data object Identifier : TokenType("IDENTIFIER")
data object NumberLiteral : TokenType("NUMBER_LITERAL")
data object StringLiteral : TokenType("STRING_LITERAL")
data object Plus : TokenType("PLUS")
data object Minus : TokenType("MINUS")
data object Multiply : TokenType("MULTIPLY")
data object Divide : TokenType("DIVIDE")
data object Whitespace : TokenType("WHITESPACE")
data object Declaration : TokenType("DECLARATION")
data object Assignment : TokenType("ASSIGNMENT")
data object Ending : TokenType("ENDING")
