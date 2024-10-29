package parsertest

import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import parser.elements.ParserProvider
import token.Assignment
import token.Boolean
import token.CloseBrace
import token.CloseParenthesis
import token.Else
import token.Ending
import token.Identifier
import token.If
import token.NativeMethod
import token.OpenBrace
import token.OpenParenthesis
import token.Position
import token.StringLiteral
import token.Token

class IllegalStatementTest {

    private val pos = Position.initial()

    @Test
    fun `= alone`() {

        val tokenList = listOf(
            Token(Assignment, "=", pos),
            Token(Ending, ";", pos)
        )

        val parser = ParserProvider(tokenList.listIterator()).getParser("1.1")
        assertThrows<IllegalArgumentException> {
            parser.parse()
        }
    }

    @Test
    fun `missing open parenthesis in if`() {

        val tokenList = listOf(
            Token(If, "if", pos),
            Token(OpenBrace, "{", pos),
            Token(Boolean, "true", pos),
            Token(CloseParenthesis, ")", pos),
            Token(OpenBrace, "{", pos),
            Token(CloseBrace, "}", pos)
        )

        val parser = ParserProvider(tokenList.listIterator()).getParser("1.1")
        assertThrows<IllegalStateException> {
            parser.parse()
        }
    }

    @Test
    fun `missing close parenthesis in if`() {

        val tokenList = listOf(
            Token(If, "if", pos),
            Token(OpenParenthesis, "(", pos),
            Token(Boolean, "true", pos),
            Token(OpenBrace, "{", pos),
            Token(CloseBrace, "}", pos)
        )

        val parser = ParserProvider(tokenList.listIterator()).getParser("1.1")
        assertThrows<IllegalArgumentException> {
            parser.parse()
        }
    }

    @Test
    fun `missing open brace in if`() {

        val tokenList = listOf(
            Token(If, "if", pos),
            Token(OpenParenthesis, "(", pos),
            Token(Boolean, "true", pos),
            Token(CloseParenthesis, ")", pos),
            Token(Identifier, "a", pos),
            Token(Ending, ";", pos)
        )

        val parser = ParserProvider(tokenList.listIterator()).getParser("1.1")
        assertThrows<IllegalArgumentException> {
            parser.parse()
        }
    }

    @Test
    fun `missing if before else`() {

        val tokenList = listOf(
            Token(Else, "else", pos),
            Token(OpenBrace, "{", pos),
            Token(CloseBrace, "}", pos)
        )

        val parser = ParserProvider(tokenList.listIterator()).getParser("1.1")
        assertThrows<NoSuchElementException> {
            parser.parse()
        }
    }

    @Test
    fun `method unsupported`() {

        val tokenList = listOf(
            Token(Identifier, "name", pos),
            Token(Assignment, "=", pos),
            Token(NativeMethod, "fakeMethod", pos),
            Token(OpenParenthesis, "(", pos),
            Token(StringLiteral, "\"name\"", pos),
            Token(CloseParenthesis, ")", pos),
            Token(Ending, ";", pos)
        )

        val parser = ParserProvider(tokenList.listIterator()).getParser("1.1")
        assertThrows<IllegalArgumentException> {
            parser.parse()
        }
    }

    @Test
    fun `invalid method for version`() {

        val tokenList = listOf(
            Token(Identifier, "name", pos),
            Token(Assignment, "=", pos),
            Token(NativeMethod, "readEnv", pos),
            Token(OpenParenthesis, "(", pos),
            Token(StringLiteral, "\"name\"", pos),
            Token(CloseParenthesis, ")", pos),
            Token(Ending, ";", pos)
        )

        val parser = ParserProvider(tokenList.listIterator()).getParser("1.0")
        assertThrows<IllegalArgumentException> {
            parser.parse()
        }
    }

    @Test
    fun `invalid method for version 2`() {

        val tokenList = listOf(
            Token(Identifier, "name", pos),
            Token(Assignment, "=", pos),
            Token(NativeMethod, "readInput", pos),
            Token(OpenParenthesis, "(", pos),
            Token(StringLiteral, "\"name\"", pos),
            Token(CloseParenthesis, ")", pos),
            Token(Ending, ";", pos)
        )

        val parser = ParserProvider(tokenList.listIterator()).getParser("1.0")
        assertThrows<IllegalArgumentException> {
            parser.parse()
        }
    }
}
