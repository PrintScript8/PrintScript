import formatter.FormatterImpl
import node.dynamic.VariableType
import node.staticpkg.ExpressionType
import node.staticpkg.StaticNode
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import type.LiteralType
import type.LiteralValue

class ExpressionTest {

    private val formatter = FormatterImpl()

    @Test
    fun testExpression() {
        val expression: StaticNode = ExpressionType(
            VariableType("name", null, false),
            LiteralType(LiteralValue.NumberValue(10))
        )

        val result = formatter.execute(listOf(expression))
        val expected = "name = 10;"

        assertEquals(expected, result)
    }
}