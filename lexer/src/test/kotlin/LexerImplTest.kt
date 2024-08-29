import lexer.LexerImpl
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import rule.AssignationRule
import rule.CloseParenthesisRule
import rule.DeclarationRule
import rule.DivideOperation
import rule.EndingRule
import rule.IdentifierRule
import rule.MinusOperation
import rule.ModifierRule
import rule.MultiplyOperation
import rule.NativeMethodRule
import rule.NumberLiteralRule
import rule.OpenParenthesisRule
import rule.OperationRule
import rule.ParenthesisRule
import rule.PlusOperation
import rule.StringLiteralRule
import rule.TypeIdRule
import rule.WhiteSpaceRule
import token.Assignment
import token.CloseParenthesis
import token.Declaration
import token.Ending
import token.Identifier
import token.NumberLiteral
import token.OpenParenthesis
import token.Position
import token.StringLiteral
import token.TokenImpl

class LexerImplTest {

    private val rules = listOf(
        ModifierRule(),
        NativeMethodRule(),
        TypeIdRule(),
        WhiteSpaceRule(),
        IdentifierRule(),
        NumberLiteralRule(),
        StringLiteralRule(),
        DeclarationRule(),
        AssignationRule(),
        EndingRule(),
        OperationRule(listOf(PlusOperation, MinusOperation, MultiplyOperation, DivideOperation)),
        ParenthesisRule(listOf(OpenParenthesisRule, CloseParenthesisRule))
    )

    private val lexerImpl = LexerImpl(rules)

    @Test
    fun `test declaration tokenization`() {
        val input = ":"
        val expectedTokens = listOf(
            TokenImpl(Declaration, ":", Position(1, 1, 1))
        )
        val tokens = lexerImpl.tokenize(input)
        assertEquals(expectedTokens, tokens)
    }

    @Test
    fun `test ending tokenization`() {
        val input = ";"
        val expectedTokens = listOf(
            TokenImpl(Ending, ";", Position(1, 1, 1))
        )
        val tokens = lexerImpl.tokenize(input)
        assertEquals(expectedTokens, tokens)
    }

    @Test
    fun `test combined tokenization with declaration and ending`() {
        val input = "var1 : 123 ;"
        val expectedTokens = listOf(
            TokenImpl(Identifier, "var1", Position(1, 1, 4)),
            TokenImpl(Declaration, ":", Position(1, 6, 6)),
            TokenImpl(NumberLiteral, "123", Position(1, 8, 10)),
            TokenImpl(Ending, ";", Position(1, 12, 12))
        )
        val tokens = lexerImpl.tokenize(input)
        assertEquals(expectedTokens, tokens)
    }

    @Test
    fun `test different rows test`() {
        val input = "var1 : 123 ;\nvar2 : 456 ;"
        val expectedTokens = listOf(
            TokenImpl(Identifier, "var1", Position(1, 1, 4)),
            TokenImpl(Declaration, ":", Position(1, 6, 6)),
            TokenImpl(NumberLiteral, "123", Position(1, 8, 10)),
            TokenImpl(Ending, ";", Position(1, 12, 12)),
            TokenImpl(Identifier, "var2", Position(2, 1, 4)),
            TokenImpl(Declaration, ":", Position(2, 6, 6)),
            TokenImpl(NumberLiteral, "456", Position(2, 8, 10)),
            TokenImpl(Ending, ";", Position(2, 12, 12))
        )
        val tokens = lexerImpl.tokenize(input)
        assertEquals(expectedTokens, tokens)
    }

    @Test
    fun `illegal character test`() {
        val input = "var1 : @ + 3"
        try {
            lexerImpl.tokenize(input)
        } catch (e: IllegalArgumentException) {
            assertEquals("Unexpected character at row 1, column 8", e.message)
        }
    }

    @Test
    fun `parenthesis test`() {
        val input = "(var1 + 3) * 2"
        val expectedTokens = listOf(
            TokenImpl(OpenParenthesis, "(", Position(1, 1, 1)),
            TokenImpl(Identifier, "var1", Position(1, 2, 5)),
            TokenImpl(PlusOperation.tokenType, "+", Position(1, 7, 7)),
            TokenImpl(NumberLiteral, "3", Position(1, 9, 9)),
            TokenImpl(CloseParenthesis, ")", Position(1, 10, 10)),
            TokenImpl(MultiplyOperation.tokenType, "*", Position(1, 12, 12)),
            TokenImpl(NumberLiteral, "2", Position(1, 14, 14))
        )
        val tokens = lexerImpl.tokenize(input)
        assertEquals(expectedTokens, tokens)
    }

    @Test
    fun `assignation test`() {
        val input = "var1 = 3"
        val expectedTokens = listOf(
            TokenImpl(Identifier, "var1", Position(1, 1, 4)),
            TokenImpl(Assignment, "=", Position(1, 6, 6)),
            TokenImpl(NumberLiteral, "3", Position(1, 8, 8))
        )
        val tokens = lexerImpl.tokenize(input)
        assertEquals(expectedTokens, tokens)
    }

    @Test
    fun `string literal and toString test`() {
        val input = "\"hello\""
        val expectedTokens = listOf(
            TokenImpl(StringLiteral, "\"hello\"", Position(1, 1, 7))
        )
        val tokens = lexerImpl.tokenize(input)
        assertEquals(expectedTokens[0].toString(), tokens[0].toString())
    }
}
