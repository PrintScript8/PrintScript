package type

import interpreter.InterpreterImpl
import node.dynamic.MultiplyType
import node.dynamic.VariableType
import node.staticpkg.AssignationType
import node.staticpkg.DeclarationType
import node.staticpkg.IdentifierType
import node.staticpkg.ModifierType
import node.staticpkg.PrintLnType
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertNotNull
import org.junit.jupiter.api.Test
import visitor.DynamicInterpreterVisitor
import visitor.StaticInterpreterVisitor

class MultiplyTypeTest {

    @Test
    fun testSumTypeCreation() {
        val multiplyType = MultiplyType(
            LiteralType(LiteralValue.NumberValue(5)),
            LiteralType(LiteralValue.NumberValue(5)),
            null
        )
        assertNotNull(multiplyType)
    }

    @Test
    fun testNumberResult() {
        val multiplyType = MultiplyType(
            LiteralType(LiteralValue.NumberValue(5)),
            LiteralType(LiteralValue.NumberValue(5)),
            null
        )
        val dynamicVisitor = DynamicInterpreterVisitor(InterpreterImpl())
        multiplyType.visit(dynamicVisitor)
        val result: LiteralValue = multiplyType.result!!
        assertEquals("25", result.toString())
    }

    @Test
    fun testWithVariable() {
        val assignationType = AssignationType(
            DeclarationType(
                ModifierType("let", true),
                IdentifierType(),
                "a"
            ),
            LiteralType(LiteralValue.NumberValue(5))
        )
        val multiplyType = MultiplyType(
            LiteralType(LiteralValue.NumberValue(1)),
            VariableType("a", null, false),
            null
        )
        val interpreter = InterpreterImpl()
        val dynamicVisitor = DynamicInterpreterVisitor(interpreter)
        val staticVisitor = StaticInterpreterVisitor(interpreter)
        assignationType.visit(staticVisitor)
        multiplyType.visit(dynamicVisitor)
        val result: LiteralValue = multiplyType.result!!
        assertEquals("5", result.toString())
    }

    @Test
    fun testPrint() {
        val multiplyType = MultiplyType(
            LiteralType(LiteralValue.NumberValue(1)),
            LiteralType(LiteralValue.NumberValue(5)),
            null
        )
        val printLnType = PrintLnType(multiplyType)
        val interpreter = InterpreterImpl()
        val dynamicVisitor = DynamicInterpreterVisitor(interpreter)
        val staticVisitor = StaticInterpreterVisitor(interpreter)
        multiplyType.visit(dynamicVisitor)
        printLnType.visit(staticVisitor)
    }
}
