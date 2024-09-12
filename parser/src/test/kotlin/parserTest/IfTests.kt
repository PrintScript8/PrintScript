package parserTest

import node.dynamic.LiteralType
import node.dynamic.LiteralValue
import node.dynamic.VariableType
import node.staticpkg.ExpressionType
import node.staticpkg.IfElseType
import node.staticpkg.PrintLnType
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import parser.elements.ParserProvider
import token.*
import token.Boolean

class IfTests {

    @Test
    fun `if test`() {

        val pos = Position.initial()

        val tokenIterator = listOf(
            Token(If, "if", pos),
            Token(OpenParenthesis, "(", pos),
            Token(Boolean, "true", pos),
            Token(CloseParenthesis, ")", pos),
            Token(OpenBrace, "{", pos),
            Token(NativeMethod, "println", pos),
            Token(OpenParenthesis, "(", pos),
            Token(StringLiteral, "\"Hello, World!\"", pos),
            Token(CloseParenthesis, ")", pos),
            Token(Ending, ";", pos),
            Token(CloseBrace, "}", pos),
        )

        val parser = ParserProvider(tokenIterator.iterator()).getParser("1.1")

        val output = parser.iterator()

        val expected = IfElseType(
            listOf(PrintLnType(LiteralType(LiteralValue.StringValue("Hello, World!")))),
            LiteralType(LiteralValue.BooleanValue(true)),
            null
        )

        assertEquals(output.next().format("1.1"), expected.format("1.1"))
    }

    @Test
    fun iftest2() {

        val pos = Position.initial()

        val tokenIterator = listOf(
            Token(If, "if", pos),
            Token(OpenParenthesis, "(", pos),
            Token(Boolean, "true", pos),
            Token(CloseParenthesis, ")", pos),
            Token(OpenBrace, "{", pos),
            Token(NativeMethod, "println", pos),
            Token(OpenParenthesis, "(", pos),
            Token(StringLiteral, "\"Hello, World!\"", pos),
            Token(CloseParenthesis, ")", pos),
            Token(Ending, ";", pos),
            Token(NativeMethod, "a", pos),
            Token(OpenParenthesis, "=", pos),
            Token(StringLiteral, "\"I lost the Game\"", pos),
            Token(Ending, ";", pos),
            Token(CloseBrace, "}", pos),
        )

        val parser = ParserProvider(tokenIterator.iterator()).getParser("1.1")

        val output = parser.iterator()

        val expected = IfElseType(
            listOf(PrintLnType(LiteralType(LiteralValue.StringValue("Hello, World!"))),
                ExpressionType(
                    VariableType("a", null, true),
                    LiteralType(LiteralValue.StringValue("I lost the Game"))
                )
            ),
            LiteralType(LiteralValue.BooleanValue(true)),
            null
        )

        assertEquals(output.next().format("1.1"), expected.format("1.1"))
    }

    @Test
    fun iftest3() {

        val pos = Position.initial()

        val tokenIterator = listOf(
            Token(If, "if", pos),
            Token(OpenParenthesis, "(", pos),
            Token(Boolean, "true", pos),
            Token(CloseParenthesis, ")", pos),
            Token(OpenBrace, "{", pos),
            Token(If, "if", pos),
            Token(OpenParenthesis, "(", pos),
            Token(Boolean, "true", pos),
            Token(CloseParenthesis, ")", pos),
            Token(OpenBrace, "{", pos),
            Token(NativeMethod, "println", pos),
            Token(OpenParenthesis, "(", pos),
            Token(StringLiteral, "\"Hello, World!\"", pos),
            Token(CloseParenthesis, ")", pos),
            Token(Ending, ";", pos),
            Token(CloseBrace, "}", pos),
            Token(CloseBrace, "}", pos)
        )

        val parser = ParserProvider(tokenIterator.iterator()).getParser("1.1")

        val output = parser.iterator()

        val expected = IfElseType(
            listOf(
                IfElseType(
                    listOf(PrintLnType(LiteralType(LiteralValue.StringValue("Hello, World!")))),
                    LiteralType(LiteralValue.BooleanValue(true)),
                    null)),
            LiteralType(LiteralValue.BooleanValue(true)),
            null
        )

        assertEquals(output.next().format("1.1"), expected.format("1.1"))
    }

}