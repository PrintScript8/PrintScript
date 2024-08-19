package parserTest

import parser.elements.Parser
import org.example.token.Token
import org.example.token.TokenImpl
import org.example.token.TokenType
import org.junit.jupiter.api.Test
import parser.elements.Parser2

class ParserTests {

    val parser: Parser = Parser2();

    @Test
    fun testDeclaration(){
        val tokenList: List<Token> = listOf(
            TokenImpl(TokenType.MODIFIER, "let", 1),
            TokenImpl(TokenType.IDENTIFIER_VAR, "name", 1),
            TokenImpl(TokenType.COLON, ":", 1),
            TokenImpl(TokenType.IDENTIFIER_TYPE, "String", 1),
            TokenImpl(TokenType.ENDING, ";", 1)
        )
        print(parser.parse(tokenList).toString())
    }
}