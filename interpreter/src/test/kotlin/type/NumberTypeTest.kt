package type

import interpreter.InterpreterImpl
import node.dynamic.LiteralType
import node.dynamic.LiteralValue
import node.staticpkg.PrintLnType
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertNotNull
import org.junit.jupiter.api.Test
import visitor.DynamicInterpreterVisitor
import visitor.StaticInterpreterVisitor

class NumberTypeTest {

    @Test
    fun testNumberTypeCreation() {
        val numberType = LiteralType(LiteralValue.NumberValue(5))
        assertNotNull(numberType)
    }

    @Test
    fun testNumberTypeResultType() {
        val numberType = LiteralType(LiteralValue.NumberValue(5))
        val dynamicVisitor = DynamicInterpreterVisitor(InterpreterImpl())
        numberType.visit(dynamicVisitor)
        val result: LiteralValue = numberType.result!!
        assertEquals("5", result.toString())
    }

    @Test
    fun testPrint() {
        val numberType = LiteralType(LiteralValue.NumberValue(1))
        val numberPrintLnType = PrintLnType(numberType)
        val dynamicVisitor = DynamicInterpreterVisitor(InterpreterImpl())
        val staticVisitor = StaticInterpreterVisitor(InterpreterImpl())
        numberType.visit(dynamicVisitor)
        numberPrintLnType.visit(staticVisitor)
    }
}
