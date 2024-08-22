import linter.LinterProvider
import node.dynamic.SumType
import node.staticpkg.PrintLnType
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import type.LiteralType
import type.LiteralValue

class LiteralArgumentTest {
    private val linter = LinterProvider().provideLinter("{ \"case\": \"camelCase\" , \"argument\": \"literal\" }")

    @Test
    fun testValidStringLiteral() {
        val root = PrintLnType(
            LiteralType(LiteralValue.StringValue("Hello World"))
        )

        assertEquals(linter.lint(listOf(root)), emptyList<Any>())
    }

    @Test
    fun testValidNumberLiteral() {
        val root = PrintLnType(
            LiteralType(LiteralValue.NumberValue(5))
        )

        assertEquals(linter.lint(listOf(root)), emptyList<Any>())
    }

    @Test
    fun testInvalidExpression() {
        val root = PrintLnType(
            SumType(
                LiteralType(LiteralValue.NumberValue(5)),
                LiteralType(LiteralValue.NumberValue(5)),
                null
            )
        )

        assertEquals(
            linter.lint(listOf(root)).first().toString(),
            "Error(type=ERROR, message='Only literal is allowed as argument')"
        )
    }
}
