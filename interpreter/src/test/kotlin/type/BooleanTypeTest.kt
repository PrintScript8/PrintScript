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

class BooleanTypeTest {

    @Test
    fun testNumberTypeCreation() {
        val boolType = LiteralType(LiteralValue.BooleanValue(false))
        assertNotNull(boolType)
    }

    @Test
    fun testNumberTypeResultType() {
        val boolType = LiteralType(LiteralValue.BooleanValue(false))
        val dynamicVisitor = DynamicInterpreterVisitor(InterpreterImpl())
        boolType.visit(dynamicVisitor)
        val result: LiteralValue = boolType.result!!
        assertEquals("false", result.toString())
    }

    @Test
    fun testPrint() {
        val boolType = LiteralType(LiteralValue.BooleanValue(false))
        val numberPrintLnType = PrintLnType(boolType)
        val dynamicVisitor = DynamicInterpreterVisitor(InterpreterImpl())
        val staticVisitor = StaticInterpreterVisitor(InterpreterImpl())
        boolType.visit(dynamicVisitor)
        numberPrintLnType.visit(staticVisitor)
    }
}
