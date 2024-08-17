package type

import interpreter.InterpreterImpl
import node.staticpkg.StaticNode
import node.dynamic.SumType
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import visitor.InterpreterVisitor

class SumTypeTest {

    @Test
    fun testSumTypeCreation() {
        val sumType = SumType(
            LiteralType(LiteralValue.NumberValue(5)),
            LiteralType(LiteralValue.NumberValue(5)),
            null)
        assertNotNull(sumType)
    }

    @Test
    fun testSumNumberResult() {
        val sumType = SumType(
            LiteralType(LiteralValue.NumberValue(5)),
            LiteralType(LiteralValue.NumberValue(5)),
            null)
        val dynamicVisitor = InterpreterVisitor(InterpreterImpl())
        sumType.visit(dynamicVisitor)
        val result: LiteralValue = sumType.result!!
        assertEquals("10", result.toString())
    }

    @Test
    fun testSumNumberStringResult() {
        val sumType = SumType(
            LiteralType(LiteralValue.NumberValue(5)),
            LiteralType(LiteralValue.StringValue("5")),
            null)
        val dynamicVisitor = InterpreterVisitor(InterpreterImpl())
        sumType.visit(dynamicVisitor)
        val result: LiteralValue = sumType.result!!
        assertEquals("55", result.toString())
    }

    @Test
    fun testSumStringResult() {
        val sumType = SumType(
            LiteralType(LiteralValue.StringValue("Hello ")),
            LiteralType(LiteralValue.StringValue("World!")),
            null)
        val dynamicVisitor = InterpreterVisitor(InterpreterImpl())
        sumType.visit(dynamicVisitor)
        val result: LiteralValue = sumType.result!!
        assertEquals("Hello World!", result.toString())
    }
}