package parserTest

import org.example.parser.Parser
import org.example.parser.ParserImpl
import org.example.token.Token
import org.example.token.TokenImpl
import org.example.token.TokenType
import org.junit.jupiter.api.Test

class ParserTests {

    val parser: Parser = ParserImpl();

    @Test
    fun testDeclaration(){
        val tokenList: List<Token> = listOf(
            TokenImpl(TokenType.MODIFIER, "let", 1),
            TokenImpl(TokenType.IDENTIFIER_VAR, "name", 1),
            TokenImpl(TokenType.DECLARATION, ":", 1),
            TokenImpl(TokenType.IDENTIFIER_TYPE, "String", 1),
            TokenImpl(TokenType.ENDING, ";", 1)
        )
        print(parser.parse(tokenList).toString())
    }
}