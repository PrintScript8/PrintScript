package parsertest

import org.junit.jupiter.api.Test
import parser.elements.ParserProvider
import token.Assignment
import token.Boolean
import token.CloseBrace
import token.CloseParenthesis
import token.Else
import token.Ending
import token.Identifier
import token.If
import token.NumberLiteral
import token.OpenBrace
import token.OpenParenthesis
import token.Position
import token.Token

class IfElseTest {

    private val pos = Position.initial()

    private val ifTokens = listOf(
        Token(If, "if", pos),
        Token(OpenParenthesis, "(", pos),
        Token(Boolean, "true", pos),
        Token(CloseParenthesis, ")", pos),
        Token(OpenBrace, "{", pos),
        Token(Identifier, "a", pos),
        Token(Assignment, "=", pos),
        Token(NumberLiteral, "1", pos),
        Token(Ending, ";", pos),
        Token(CloseBrace, "}", pos),
    )

    @Test
    fun ifBranchTest() {
        val parser = ParserProvider(ifTokens.listIterator()).getParser("1.1")
        parser.parse()
    }

    @Test
    fun elseBranchTest() {
        val elseTokens = listOf(
            Token(Else, "else", pos),
            Token(OpenBrace, "{", pos),
            Token(Identifier, "a", pos),
            Token(Assignment, "=", pos),
            Token(NumberLiteral, "2", pos),
            Token(Ending, ";", pos),
            Token(CloseBrace, "}", pos),
        )
        val tokens = ifTokens + elseTokens
        val parser = ParserProvider(tokens.listIterator()).getParser("1.1")
        parser.parse()
    }
}
