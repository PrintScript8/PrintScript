package type

import interpreter.IntepreterProvider
import node.dynamic.LiteralType
import node.dynamic.LiteralValue
import node.staticpkg.PrintLnType
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertNotNull
import org.junit.jupiter.api.Test

class NumberTypeTest {

    @Test
    fun testNumberTypeCreation() {
        val numberType = LiteralType(LiteralValue.NumberValue(5))
        assertNotNull(numberType)
    }

    @Test
    fun testPrint() {
        val numberType = LiteralType(LiteralValue.NumberValue(1))
        val numberPrintLnType = PrintLnType(numberType)
        val interpreter = IntepreterProvider(
            listOf(numberPrintLnType).iterator()
        ).provideInterpreter("1.0")
        assertEquals(listOf("1"), interpreter.execute())
    }
}
