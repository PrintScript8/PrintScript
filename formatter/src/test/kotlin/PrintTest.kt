import formatter.FormatterImpl
import node.staticpkg.PrintLnType
import node.staticpkg.StaticNode
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertNotNull
import org.junit.jupiter.api.Test
import type.LiteralType
import type.LiteralValue

class PrintTest {

    private val formatter = FormatterImpl()

    @Test
    fun printTestWithString() {
        val printType: StaticNode = PrintLnType(
            LiteralType(LiteralValue.StringValue("Hello, World!")))
        assertNotNull(printType)

        formatter.execute(listOf(printType))

        val result = formatter.getOutput()
        val expected = "println(\"Hello, World!\");"

        assertEquals(expected, result)
    }

    @Test
    fun printTestWithOperation() {
        TODO()
    }

    @Test
    fun printTestWithVariable() {
        TODO()
    }

    @Test
    fun printTestWithMultipleOperations() {
        TODO()
    }
}