import formatter.FormatterImpl
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

class PrintTest {

    private val formatter = FormatterImpl()

    @Test
    fun printTestWithString() {
        val printType: StaticNode = PrintLnType(
            LiteralType(LiteralValue.StringValue("Hello, World!"))
        )
        assertNotNull(printType)

        val result = formatter.execute(listOf(printType))
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

        val result = formatter.execute(listOf(printType))
        val expected = "println(1 + 2);"

        assertEquals(expected, result)
    }

    @Test
    fun printTestWithVariable() {
        val variable = VariableType("a", null, false)
        val printType: StaticNode = PrintLnType(variable)

        val result = formatter.execute(listOf(printType))
        val expected = "println(a);"

        assertEquals(expected, result)
    }

    @Test
    fun printTestWithMultipleOperations() {
        val sum = SumType(
            VariableType("x", null, false),
            LiteralType(LiteralValue.StringValue("y")),
            null
        )

        val multiply = MultiplyType(
            LiteralType(LiteralValue.NumberValue(3)),
            LiteralType(LiteralValue.NumberValue(4)),
            null
        )

        val printType: StaticNode = PrintLnType(SumType(sum, multiply, null))

        val result = formatter.execute(listOf(printType))
        val expected = "println(x + \"y\" + 3 * 4);"

        assertEquals(expected, result)
    }
}
