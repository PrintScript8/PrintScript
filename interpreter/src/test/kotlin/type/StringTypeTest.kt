package type

import interpreter.InterpreterProvider
import node.dynamic.LiteralType
import node.dynamic.LiteralValue
import node.staticpkg.PrintLnType
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertNotNull
import org.junit.jupiter.api.Test

class StringTypeTest {

    @Test
    fun testStringTypeCreation() {
        val stringType = LiteralType(LiteralValue.StringValue("Hello"))
        assertNotNull(stringType)
    }

    @Test
    fun testStringTypeResultType() {
        val stringType = LiteralType(LiteralValue.StringValue("Hello"))
        val numberPrintLnType = PrintLnType(stringType)
        val interpreter = InterpreterProvider(
            listOf(
                numberPrintLnType
            ).iterator()
        ).provideInterpreter("1.0")
        assertEquals("Hello", interpreter.iterator().next())
    }
}
