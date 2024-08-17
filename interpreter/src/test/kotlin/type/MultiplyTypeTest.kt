package type

import interpreter.InterpreterImpl
import node.dynamic.MultiplyType
import node.staticpkg.StaticNode
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import visitor.InterpreterVisitor

class MultiplyTypeTest {

    @Test
    fun testSumTypeCreation() {
        val multiplyType = MultiplyType(
            LiteralType(LiteralValue.NumberValue(5)),
            LiteralType(LiteralValue.NumberValue(5)),
            null)
        assertNotNull(multiplyType)
    }

    @Test
    fun testNumberResult() {
        val multiplyType = MultiplyType(
            LiteralType(LiteralValue.NumberValue(5)),
            LiteralType(LiteralValue.NumberValue(5)),
            null)
        val dynamicVisitor = InterpreterVisitor(InterpreterImpl())
        multiplyType.visit(dynamicVisitor)
        val result: LiteralValue = multiplyType.result!!
        assertEquals("25", result.toString())
    }
}