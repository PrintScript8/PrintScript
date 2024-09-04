package test

import builder.LexerProvider
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
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
import java.io.FileInputStream
import java.nio.file.Paths

class LexerTestV2 {

    private val lexer = LexerProvider().getLexerV2()

    @Test
    fun `test if-else tokenization`() {
        val input = FileInputStream(getAbsolutePath("src/test/kotlin/textfile/testv2/file1"))
        val expectedTokens = listOf(
            Token(If, "if", Position(1, 1, 2)),
            Token(Boolean, "false", Position(1, 4, 8)),
            Token(Else, "else", Position(1, 10, 13)),

        )
        val tokens = lexer.tokenize(input)
        assertEquals(expectedTokens, tokens)
    }

    @Test
    fun `if-else-bool brace tokenization`() {
        val input = FileInputStream(getAbsolutePath("src/test/kotlin/textfile/testv2/file2"))
        val expectedTokens = listOf(
            Token(If, "if", Position(1, 1, 2)),
            Token(OpenParenthesis, "(", Position(1, 4, 4)),
            Token(Boolean, "true", Position(1, 5, 8)),
            Token(CloseParenthesis, ")", Position(1, 9, 9)),
            Token(OpenBrace, "{", Position(1, 11, 11)),
            Token(Identifier, "a", Position(2, 5, 5)),
            Token(Assignment, "=", Position(2, 7, 7)),
            Token(NumberLiteral, "3", Position(2, 9, 9)),
            Token(Ending, ";", Position(2, 10, 10)),
            Token(CloseBrace, "}", Position(3, 1, 1)),
            Token(Else, "else", Position(3, 3, 6)),
            Token(OpenBrace, "{", Position(3, 8, 8)),
            Token(Identifier, "b", Position(4, 5, 5)),
            Token(Assignment, "=", Position(4, 7, 7)),
            Token(NumberLiteral, "4", Position(4, 9, 9)),
            Token(Ending, ";", Position(4, 10, 10)),
            Token(CloseBrace, "}", Position(5, 1, 1))
        )
        val tokens = lexer.tokenize(input)
        assertEquals(expectedTokens, tokens)
    }

    @Test
    fun `fake if-else test`() {
        val input = FileInputStream(getAbsolutePath("src/test/kotlin/textfile/testv2/file3"))
        val expectedTokens = listOf(
            Token(Identifier, "ifer", Position(1, 1, 4)),
            Token(Identifier, "elser", Position(1, 6, 10))
        )
        val tokens = lexer.tokenize(input)
        assertEquals(expectedTokens, tokens)
    }

    @Test
    fun `position to String test`() {
        val position = Position(1, 1, 1)
        assertEquals("row=1, startColumn=1, endColumn=1", position.toString())
    }

    @Test
    fun `token to String`() {
        val token = Token(If, "if", Position(1, 1, 2))
        val expected = "IF | \"if\" | row = 1 | [1, 2] columns"
        assertEquals(expected, token.toString())
    }

    private fun getAbsolutePath(relativePath: String): String {
        val projectRoot = Paths.get("").toAbsolutePath().toString()
        return Paths.get(projectRoot, relativePath).toString()
    }
}
