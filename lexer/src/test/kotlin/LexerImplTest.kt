import lexer.LexerImpl
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import rule.AssignationRule
import rule.DeclarationRule
import rule.DivideOperation
import rule.EndingRule
import rule.IdentifierRule
import rule.MinusOperation
import rule.MultiplyOperation
import rule.NumberLiteralRule
import rule.OperationRule
import rule.PlusOperation
import rule.StringLiteralRule
import rule.WhiteSpaceRule
import token.Declaration
import token.Ending
import token.Identifier
import token.NumberLiteral
import token.Position
import token.TokenImpl

class LexerImplTest {

    private val rules = listOf(
        WhiteSpaceRule(),
        IdentifierRule(),
        NumberLiteralRule(),
        StringLiteralRule(),
        DeclarationRule(),
        AssignationRule(),
        EndingRule(),
        OperationRule(listOf(PlusOperation, MinusOperation, MultiplyOperation, DivideOperation))
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
}
