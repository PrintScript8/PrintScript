package type

import interpreter.InterpreterImpl
import node.dynamic.DivisionType
import node.dynamic.VariableType
import node.staticpkg.*
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import visitor.InterpreterVisitor

class NumberTypeTest {

    @Test
    fun testNumberTypeCreation() {
        val numberType = LiteralType(LiteralValue.NumberValue(5))
        assertNotNull(numberType)
    }

    @Test
    fun testNumberTypeResultType() {
        val numberType = LiteralType(LiteralValue.NumberValue(5))
        val dynamicVisitor = InterpreterVisitor(InterpreterImpl())
        numberType.visit(dynamicVisitor)
        val result: LiteralValue = numberType.result!!
        assertEquals("5", result.toString())
    }

}