
import node.dynamic.LiteralType
import node.dynamic.LiteralValue
import node.dynamic.VariableType
import node.staticpkg.ExpressionType
import node.staticpkg.IfElseType
import node.staticpkg.PrintLnType
import org.junit.jupiter.api.Assertions.assertEquals
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
import token.NativeMethod
import token.OpenBrace
import token.OpenParenthesis
import token.Position
import token.StringLiteral
import token.Token

class IfTests {

    @Test
    fun `if test`() {

        val tokenIterator = listOf(
            Token(If, "if", pos(1)),
            Token(OpenParenthesis, "(", pos(2)),
            Token(Boolean, "true", pos(3)),
            Token(CloseParenthesis, ")", pos(4)),
            Token(OpenBrace, "{", pos(5)),
            Token(NativeMethod, "println", pos(6)),
            Token(OpenParenthesis, "(", pos(7)),
            Token(StringLiteral, "\"Hello, World!\"", pos(8)),
            Token(CloseParenthesis, ")", pos(9)),
            Token(Ending, ";", pos(10)),
            Token(CloseBrace, "}", pos(11)),
        )

        val parser = ParserProvider(tokenIterator.iterator()).getParser("1.1")

        val output = parser.iterator()

        val expected = IfElseType(
            listOf(PrintLnType(LiteralType(LiteralValue.StringValue("Hello, World!")))),
            LiteralType(LiteralValue.BooleanValue(true)),
            null
        )

        assertEquals(expected.format("1.1", 0), output.next().format("1.1", 0))
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
            Token(Identifier, "a", pos),
            Token(Assignment, "=", pos),
            Token(StringLiteral, "\"I lost the Game\"", pos),
            Token(Ending, ";", pos),
            Token(CloseBrace, "}", pos),
        )

        val parser = ParserProvider(tokenIterator.iterator()).getParser("1.1")

        val output = parser.iterator()

        val expected = IfElseType(
            listOf(
                PrintLnType(LiteralType(LiteralValue.StringValue("Hello, World!"))),
                ExpressionType(
                    VariableType("a", null),
                    LiteralType(LiteralValue.StringValue("I lost the Game"))
                )
            ),
            LiteralType(LiteralValue.BooleanValue(true)),
            null
        )

        assertEquals(expected.format("1.1", 0), output.next().format("1.1", 0))
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
                    null
                )
            ),
            LiteralType(LiteralValue.BooleanValue(true)),
            null
        )

        assertEquals(expected.format("1.1", 0), output.next().format("1.1", 0))
    }

    private fun pos(number: Int): Position {
        return Position(number, 0, 0)
    }

    @Test
    fun `if test4`() {

        val tokenIterator = listOf(
            Token(If, "if", pos(1)),
            Token(OpenParenthesis, "(", pos(2)),
            Token(Boolean, "true", pos(3)),
            Token(CloseParenthesis, ")", pos(4)),
            Token(OpenBrace, "{", pos(5)),
            Token(NativeMethod, "println", pos(6)),
            Token(OpenParenthesis, "(", pos(7)),
            Token(StringLiteral, "\"Hello, World!\"", pos(8)),
            Token(CloseParenthesis, ")", pos(9)),
            Token(Ending, ";", pos(10)),
            Token(CloseBrace, "}", pos(11)),
            Token(Else, "else", pos(12)),
            Token(OpenBrace, "{", pos(13)),
            Token(Identifier, "a", pos(14)),
            Token(Assignment, "=", pos(15)),
            Token(StringLiteral, "\"I lost the Game\"", pos(16)),
            Token(Ending, ";", pos(17)),
            Token(CloseBrace, "}", pos(18)),
        )

        val parser = ParserProvider(tokenIterator.iterator()).getParser("1.1")

        val output = parser.iterator()

        val expected = IfElseType(
            listOf(
                PrintLnType(LiteralType(LiteralValue.StringValue("Hello, World!"))),
            ),
            LiteralType(LiteralValue.BooleanValue(true)),
            listOf(
                ExpressionType(
                    VariableType("a", null),
                    LiteralType(LiteralValue.StringValue("I lost the Game"))
                )
            )
        )

        assertEquals(expected.format("1.1", 0), output.next().format("1.1", 0))
    }

    @Test
    fun `if test5`() {

        val tokenIterator = listOf(
            Token(If, "if", pos(1)),
            Token(OpenParenthesis, "(", pos(2)),
            Token(Boolean, "true", pos(3)),
            Token(CloseParenthesis, ")", pos(4)),
            Token(OpenBrace, "{", pos(5)),
            Token(NativeMethod, "println", pos(6)),
            Token(OpenParenthesis, "(", pos(7)),
            Token(StringLiteral, "\"Hello, World!\"", pos(8)),
            Token(CloseParenthesis, ")", pos(9)),
            Token(Ending, ";", pos(10)),
            Token(CloseBrace, "}", pos(11)),
            Token(Else, "else", pos(12)),
            Token(OpenBrace, "{", pos(13)),
            Token(If, "if", pos(14)),
            Token(OpenParenthesis, "(", pos(15)),
            Token(Boolean, "false", pos(16)),
            Token(CloseParenthesis, ")", pos(17)),
            Token(OpenBrace, "{", pos(18)),
            Token(Identifier, "a", pos(19)),
            Token(Assignment, "=", pos(20)),
            Token(StringLiteral, "\"I lost the Game\"", pos(21)),
            Token(Ending, ";", pos(22)),
            Token(CloseBrace, "}", pos(23)),
            Token(CloseBrace, "}", pos(24)),
        )

        val parser = ParserProvider(tokenIterator.iterator()).getParser("1.1")

        val output = parser.iterator()

        val expected = IfElseType(
            listOf(
                PrintLnType(LiteralType(LiteralValue.StringValue("Hello, World!"))),
            ),
            LiteralType(LiteralValue.BooleanValue(true)),
            listOf(
                IfElseType(
                    listOf(
                        ExpressionType(
                            VariableType("a", null),
                            LiteralType(LiteralValue.StringValue("I lost the Game"))
                        )
                    ),
                    LiteralType(LiteralValue.BooleanValue(false)),
                    null
                )
            )
        )

        assertEquals(expected.format("1.1", 0), output.next().format("1.1", 0))
    }
}
