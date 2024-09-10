package test

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import provider.LexerProvider
import reader.InputStreamReader
import reader.ReaderInterface
import token.Assignment
import token.CloseParenthesis
import token.Declaration
import token.Ending
import token.Identifier
import token.Modifier
import token.NativeMethod
import token.NumberLiteral
import token.OpenParenthesis
import token.Plus
import token.Position
import token.StringLiteral
import token.Token
import token.TypeId
import java.io.ByteArrayInputStream
import java.io.File
import java.nio.file.Paths

class LexerInterfaceTestV1 {

    @Test
    fun `test declaration-ending tokenization`() {
        val input: ReaderInterface = getReader("src/test/kotlin/textfile/testv1/file1")
        val expectedTokens = listOf(
            Token(Declaration, ":", Position(1, 1, 1)),
            Token(Ending, ";", Position(1, 3, 3))

        )
        val lexer = LexerProvider(input).getLexer("1.0")
        val tokens = lexer.iterator().asSequence().toList()
        assertEquals(expectedTokens, tokens)
    }

    @Test
    fun `test different rows test`() {
        val input: ReaderInterface = getReader("src/test/kotlin/textfile/testv1/file2")
        val expectedTokens = listOf(
            Token(Identifier, "var1", Position(1, 1, 4)),
            Token(Declaration, ":", Position(1, 6, 6)),
            Token(NumberLiteral, "123", Position(1, 8, 10)),
            Token(Ending, ";", Position(1, 12, 12)),
            Token(Identifier, "var2", Position(2, 1, 4)),
            Token(Declaration, ":", Position(2, 6, 6)),
            Token(NumberLiteral, "456", Position(2, 8, 10)),
            Token(Ending, ";", Position(2, 12, 12))
        )
        val lexer = LexerProvider(input).getLexer("1.0")
        val tokens = lexer.iterator().asSequence().toList()
        assertEquals(expectedTokens, tokens)
    }

    @Test
    fun `illegal character test`() {
        val input: ReaderInterface = getReader("src/test/kotlin/textfile/testv1/file3")
        try {
            val lexer = LexerProvider(input).getLexer("1.0")
            lexer.iterator().asSequence().toList()
        } catch (e: IllegalArgumentException) {
            assertEquals("Unexpected character at row 1, column 8", e.message)
        }
    }

    @Test
    fun `assignation-parenthesis-string test`() {
        val input: ReaderInterface = getReader("src/test/kotlin/textfile/testv1/file4")
        val expectedTokens = listOf(
            Token(Identifier, "var1", Position(1, 1, 4)),
            Token(Assignment, "=", Position(1, 6, 6)),
            Token(OpenParenthesis, "(", Position(1, 8, 8)),
            Token(NumberLiteral, "3", Position(1, 9, 9)),
            Token(Plus, "+", Position(1, 11, 11)),
            Token(NumberLiteral, "2", Position(1, 13, 13)),
            Token(CloseParenthesis, ")", Position(1, 14, 14)),
            Token(Plus, "+", Position(1, 16, 16)),
            Token(StringLiteral, "\"Hello\"", Position(1, 18, 24)),
        )
        val lexer = LexerProvider(input).getLexer("1.0")
        val tokens = lexer.iterator().asSequence().toList()
        assertEquals(expectedTokens, tokens)
    }

    @Test
    fun `modifier-nativeMethod test`() {
        val input: ReaderInterface = getReader("src/test/kotlin/textfile/testv1/file5")
        val expectedTokens = listOf(
            Token(Modifier, "let", Position(1, 1, 3)),
            Token(Identifier, "a", Position(1, 5, 5)),
            Token(Declaration, ":", Position(1, 6, 6)),
            Token(TypeId, "number", Position(1, 8, 13)),
            Token(Assignment, "=", Position(1, 15, 15)),
            Token(NativeMethod, "println", Position(1, 17, 23)),
            Token(OpenParenthesis, "(", Position(1, 24, 24)),
            Token(NumberLiteral, "1", Position(1, 25, 25)),
            Token(CloseParenthesis, ")", Position(1, 26, 26)),
            Token(Ending, ";", Position(1, 27, 27)),
            Token(Identifier, "if", Position(2, 1, 2)),
            Token(Ending, ";", Position(2, 3, 3)),
            Token(Identifier, "else", Position(3, 1, 4)),
            Token(Ending, ";", Position(3, 5, 5))
        )
        val lexer = LexerProvider(input).getLexer("1.0")
        val tokens = lexer.iterator().asSequence().toList()
        assertEquals(expectedTokens, tokens)
    }

    private fun getAbsolutePath(relativePath: String): String {
        val projectRoot = Paths.get("").toAbsolutePath().toString()
        return Paths.get(projectRoot, relativePath).toString()
    }

    private fun readFile(file: File): String {
        return file.readText()
    }

    private fun getReader(path: String): InputStreamReader {
        val absolutePath = getAbsolutePath(path)
        val data = readFile(File(absolutePath))
        return InputStreamReader(ByteArrayInputStream(data.toByteArray()))
    }
}
