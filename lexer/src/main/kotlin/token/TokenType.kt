package token

// Define la sealed class TokenType
sealed class TokenType(val name: String)

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

data object TypeId : TokenType("TYPE_ID") // String Number
data object NativeMethod : TokenType("NATIVE_METHOD") // println(
data object Modifier : TokenType("MODIFIER") // let const

data object OpenBrace : TokenType("OPEN_BRACE") // {
data object CloseBrace : TokenType("CLOSE_BRACE") // }

data object If : TokenType("IF") // if
data object Else : TokenType("ELSE") // else
data object Bool : TokenType("BOOL") // true false
