package token

// Define la sealed class TokenType
sealed class TokenType(val name: String)

// ver 1.0
data object Identifier : TokenType("IDENTIFIER") // a, b, c, d, hello, name
data object NumberLiteral : TokenType("NUMBER_LITERAL") // 1, 2, 3, 4, 5, 6, 7, 8, 9, 0
data object StringLiteral : TokenType("STRING_LITERAL") // "hello", "world", "123"
data object Plus : TokenType("PLUS") // +
data object Minus : TokenType("MINUS") // -
data object Multiply : TokenType("MULTIPLY") // *
data object Divide : TokenType("DIVIDE") // /
data object Whitespace : TokenType("WHITESPACE") // " "
data object Declaration : TokenType("DECLARATION") // :
data object Assignment : TokenType("ASSIGNMENT") // =
data object Ending : TokenType("ENDING") // ;
data object OpenParenthesis : TokenType("OPEN_PARENTHESIS") // (
data object CloseParenthesis : TokenType("CLOSE_PARENTHESIS") // )
data object TypeId : TokenType("TYPE_ID") // String Number boolean
data object NativeMethod : TokenType("NATIVE_METHOD") // println(
data object Modifier : TokenType("MODIFIER") // let const

// ver 1.1
data object BooleanLiteral : TokenType("BOOLEAN") // true false

// data object OpenBracket : TokenType("OPEN_BRACKET") // [
// data object CloseBracket : TokenType("CLOSE_BRACKET") // ]
// data object OpenBrace : TokenType("OPEN_BRACE") // {
// data object CloseBrace : TokenType("CLOSE_BRACE") // }
// data object Comma : TokenType("COMMA") // ,
// data object Dot : TokenType("DOT") // .
