package parsertest

import node.dynamic.LiteralValue
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertThrows
import org.junit.jupiter.api.Test

class LiteralValueTest {

    @Test
    fun testNumberValueToString() {
        val numberValue1 = LiteralValue.NumberValue(10)
        val numberValue2 = LiteralValue.NumberValue(10.5)
        assertEquals("10", numberValue1.toString())
        assertEquals("10.5", numberValue2.toString())
    }

    @Test
    fun testStringValueToString() {
        val stringValue = LiteralValue.StringValue("Hello")
        assertEquals("Hello", stringValue.toString())
    }

    @Test
    fun testPlusOperation() {
        val numberValue1 = LiteralValue.NumberValue(10)
        val numberValue2 = LiteralValue.NumberValue(5)
        val stringValue1 = LiteralValue.StringValue("Hello")
        val stringValue2 = LiteralValue.StringValue("World")

        assertEquals(LiteralValue.NumberValue(15.0), numberValue1 + numberValue2)
        assertEquals(LiteralValue.StringValue("HelloWorld"), stringValue1 + stringValue2)
        assertEquals(LiteralValue.StringValue("Hello10"), stringValue1 + numberValue1)
        assertEquals(LiteralValue.StringValue("10Hello"), numberValue1 + stringValue1)
    }

    @Test
    fun testMinusOperation() {
        val numberValue1 = LiteralValue.NumberValue(10)
        val numberValue2 = LiteralValue.NumberValue(5)

        assertEquals(LiteralValue.NumberValue(5.0), numberValue1 - numberValue2)
        assertThrows(IllegalArgumentException::class.java) {
            val stringValue = LiteralValue.StringValue("Hello")
            numberValue1 - stringValue
        }
    }

    @Test
    fun testDivOperation() {
        val numberValue1 = LiteralValue.NumberValue(10)
        val numberValue2 = LiteralValue.NumberValue(5)

        assertEquals(LiteralValue.NumberValue(2.0), numberValue1 / numberValue2)
        assertThrows(IllegalArgumentException::class.java) {
            val stringValue = LiteralValue.StringValue("Hello")
            numberValue1 / stringValue
        }
    }

    @Test
    fun testTimesOperation() {
        val numberValue1 = LiteralValue.NumberValue(10)
        val numberValue2 = LiteralValue.NumberValue(5)

        assertEquals(LiteralValue.NumberValue(50.0), numberValue1 * numberValue2)
        assertThrows(IllegalArgumentException::class.java) {
            val stringValue = LiteralValue.StringValue("Hello")
            numberValue1 * stringValue
        }
    }

    @Test
    fun testBooleanType() {
        val booleanValue1 = LiteralValue.BooleanValue(true)
        val booleanValue2 = LiteralValue.BooleanValue(false)
        assertEquals("true", booleanValue1.toString())
        assertEquals("false", booleanValue2.toString())
    }
}
