package firstversion

import node.dynamic.LiteralType
import node.dynamic.LiteralValue
import node.dynamic.MultiplyType
import node.dynamic.SumType
import node.dynamic.VariableType
import node.staticpkg.PrintLnType
import node.staticpkg.StaticNode
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertNotNull
import org.junit.jupiter.api.Test

class PrintLnTest {

    @Test
    fun printTestWithString() {
        val printType: StaticNode = PrintLnType(
            LiteralType(LiteralValue.StringValue("Hello, World!"))
        )
        assertNotNull(printType)

        val astList = listOf(printType)
        val formatter = FormatterProvider(astList.iterator()).provideFormatter("1.0")
        val result = formatter.format()
        val expected = "println(\"Hello, World!\");"

        assertEquals(expected, result)
    }

    @Test
    fun printTestWithOperation() {
        val sum = SumType(
            LiteralType(LiteralValue.NumberValue(1)),
            LiteralType(LiteralValue.NumberValue(2)),
            null
        )

        val printType: StaticNode = PrintLnType(sum)

        val astList = listOf(printType)
        val formatter = FormatterProvider(astList.iterator()).provideFormatter("1.0")
        val result = formatter.format()
        val expected = "println(1 + 2);"

        assertEquals(expected, result)
    }

    @Test
    fun printTestWithVariable() {
        val variable = VariableType("a", null)
        val printType: StaticNode = PrintLnType(variable)

        val astList = listOf(printType)
        val formatter = FormatterProvider(astList.iterator()).provideFormatter("1.0")
        val result = formatter.format()
        val expected = "println(a);"

        assertEquals(expected, result)
    }

    @Test
    fun printTestWithMultipleOperations() {
        val sum = SumType(
            VariableType("x", null),
            LiteralType(LiteralValue.StringValue("y")),
            null
        )

        val multiply = MultiplyType(
            LiteralType(LiteralValue.NumberValue(3)),
            LiteralType(LiteralValue.NumberValue(4)),
            null
        )

        val printType: StaticNode = PrintLnType(SumType(sum, multiply, null))

        val astList = listOf(printType)
        val formatter = FormatterProvider(astList.iterator()).provideFormatter("1.0")
        val result = formatter.format()
        val expected = "println(x + \"y\" + 3 * 4);"

        assertEquals(expected, result)
    }
}
