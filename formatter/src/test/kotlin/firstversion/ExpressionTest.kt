package firstversion

import node.dynamic.LiteralType
import node.dynamic.LiteralValue
import node.dynamic.VariableType
import node.staticpkg.ExpressionType
import node.staticpkg.StaticNode
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

class ExpressionTest {

    @Test
    fun testExpression() {
        val expression: StaticNode = ExpressionType(
            VariableType("name", null),
            LiteralType(LiteralValue.NumberValue(10))
        )

        val astList = listOf(expression)
        val formatter = FormatterProvider(astList.iterator()).provideFormatter("1.0")
        val result = formatter.format()
        val expected = "name = 10;"

        assertEquals(expected, result)
    }
}
