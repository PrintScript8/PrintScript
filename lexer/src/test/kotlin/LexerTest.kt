package lexer

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import rule.*
import token.*

class LexerTest {

    private val rules = listOf(
        WhitespaceRule(),
        IdentifierRule(),
        NumberLiteralRule(),
        StringLiteralRule(),
        DeclarationRule(),
        AssignmentRule(),
        EndingRule(),
        OperationRule(listOf(PlusOperation, MinusOperation, MultiplyOperation, DivideOperation))
    )
    private val lexer = Lexer(rules)

    @Test
    fun `test declaration tokenization`() {

        val input = ":"
        val expectedTokens = listOf(
            TokenImpl(Declaration, ":", Position(1, 1, 1))
        )
        val tokens = lexer.tokenize(input)
        assertEquals(expectedTokens, tokens)
    }

    @Test
    fun `test ending tokenization`() {
        val input = ";"
        val expectedTokens = listOf(
            TokenImpl(Ending, ";", Position(1, 1, 1))
        )
        val tokens = lexer.tokenize(input)
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
        val tokens = lexer.tokenize(input)
        assertEquals(expectedTokens, tokens)
    }
}
