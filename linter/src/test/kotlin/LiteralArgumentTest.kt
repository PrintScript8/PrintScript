import linter.Linter
import linter.LinterProvider
import node.dynamic.LiteralType
import node.dynamic.LiteralValue
import node.dynamic.SumType
import node.staticpkg.PrintLnType
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

class LiteralArgumentTest {
    private val linter1 = LinterProvider().provideLinter(
        "{ \"identifier_format\": \"camel case\" ," +
            " \"mandatory-variable-or-literal-in-println\": \"true\" }",
        "1.0"
    )
    private val linter2: Linter = LinterProvider().provideLinter(
        "{ \"identifier_format\": \"camel case\" ," +
                " \"mandatory-variable-or-literal-in-println\": \"true\" }",
        "1.1"
    )

    @Test
    fun testValidStringLiteral() {
        val root = PrintLnType(
            LiteralType(LiteralValue.StringValue("Hello World"))
        )

        assertEquals(linter1.lint(listOf(root).iterator()), emptyList<Any>())
        assertEquals(linter1.lint(listOf(root).iterator()), linter2.lint(listOf(root).iterator()))
    }

    @Test
    fun testValidNumberLiteral() {
        val root = PrintLnType(
            LiteralType(LiteralValue.NumberValue(5))
        )

        assertEquals(linter1.lint(listOf(root).iterator()), emptyList<Any>())
        assertEquals(linter1.lint(listOf(root).iterator()), linter2.lint(listOf(root).iterator()))
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
            linter1.lint(listOf(root).iterator()).first().toString(),
            "Error(type=ERROR, message='Only literal or variable types are allowed as argument')"
        )
        assertEquals(linter1.lint(listOf(root).iterator()).first().toString(),
            linter2.lint(listOf(root).iterator()).first().toString()
        )
    }

    @Test
    fun testValidExpression() {
        val isValidLinter = LinterProvider().provideLinter(
            "{ \"identifier_format\": \"camel case\" ," +
                " \"mandatory-variable-or-literal-in-println\": \"false\" }",
            "1.0"
        )
        val root = PrintLnType(
            SumType(
                LiteralType(LiteralValue.NumberValue(5)),
                LiteralType(LiteralValue.NumberValue(5)),
                null
            )
        )

        assertEquals(
            isValidLinter.lint(listOf(root).iterator()),
            emptyList<Any>()
        )
        assertEquals(linter1.lint(listOf(root).iterator()).first().toString(),
            linter2.lint(listOf(root).iterator()).first().toString()
        )
    }
}
