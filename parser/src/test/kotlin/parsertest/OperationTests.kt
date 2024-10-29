package parsertest

import node.dynamic.DivisionType
import node.dynamic.LiteralType
import node.dynamic.LiteralValue
import node.dynamic.MultiplyType
import node.dynamic.SubtractType
import node.dynamic.SumType
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

class OperationTests {

    @Test
    fun testSumType() {
        val left = LiteralType(LiteralValue.NumberValue(1))
        val right = LiteralType(LiteralValue.NumberValue(2))
        val sumType = SumType(left, right, null)
        sumType.result = sumType.left.result!! + sumType.right.result!!
        assertEquals(3.0, (sumType.result as LiteralValue.NumberValue).number)
        assertEquals("SumType(left='$left', right=$right)", sumType.toString())
    }

    @Test
    fun testSubtractType() {
        val left = LiteralType(LiteralValue.NumberValue(5))
        val right = LiteralType(LiteralValue.NumberValue(3))
        val subtractType = SubtractType(left, right, null)
        subtractType.result = subtractType.left.result!! - subtractType.right.result!!
        assertEquals(2.0, (subtractType.result as LiteralValue.NumberValue).number)
        assertEquals("SubtractType(left='$left', right=$right)", subtractType.toString())
    }

    @Test
    fun testMultiplyType() {
        val left = LiteralType(LiteralValue.NumberValue(4))
        val right = LiteralType(LiteralValue.NumberValue(2))
        val multiplyType = MultiplyType(left, right, null)
        multiplyType.result = multiplyType.left.result!! * multiplyType.right.result!!
        assertEquals(8.0, (multiplyType.result as LiteralValue.NumberValue).number)
        assertEquals("MultiplyType(left='$left', right=$right)", multiplyType.toString())
    }

    @Test
    fun testDivisionType() {
        val left = LiteralType(LiteralValue.NumberValue(10))
        val right = LiteralType(LiteralValue.NumberValue(2))
        val divisionType = DivisionType(left, right, null)
        divisionType.result = divisionType.left.result!! / divisionType.right.result!!
        assertEquals(5.0, (divisionType.result as LiteralValue.NumberValue).number)
        assertEquals("DivisionType(left='$left', right=$right)", divisionType.toString())
    }
}
