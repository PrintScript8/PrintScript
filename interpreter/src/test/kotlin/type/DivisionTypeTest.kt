package type

import interpreter.InterpreterImpl
import node.dynamic.DivisionType
import node.dynamic.LiteralType
import node.dynamic.LiteralValue
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

class DivisionTypeTest {

    @Test
    fun testSumTypeCreation() {
        val divisionType = DivisionType(
            LiteralType(LiteralValue.NumberValue(1)),
            LiteralType(LiteralValue.NumberValue(1)),
            null
        )
        assertNotNull(divisionType)
    }

    @Test
    fun testNumberResult() {
        val divisionType = DivisionType(
            LiteralType(LiteralValue.NumberValue(1)),
            LiteralType(LiteralValue.NumberValue(5)),
            null
        )
        val dynamicVisitor = DynamicInterpreterVisitor(InterpreterImpl())
        divisionType.visit(dynamicVisitor)
        val result: LiteralValue = divisionType.result!!
        assertEquals("0.2", result.toString())
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
        val divisionType = DivisionType(
            LiteralType(LiteralValue.NumberValue(1)),
            VariableType("a", null, false),
            null
        )
        val interpreter = InterpreterImpl()
        val dynamicVisitor = DynamicInterpreterVisitor(interpreter)
        val staticVisitor = StaticInterpreterVisitor(interpreter)
        assignationType.visit(staticVisitor)
        divisionType.visit(dynamicVisitor)
        val result: LiteralValue = divisionType.result!!
        assertEquals("0.2", result.toString())
    }

    @Test
    fun testPrint() {
        val divisionType = DivisionType(
            LiteralType(LiteralValue.NumberValue(1)),
            LiteralType(LiteralValue.NumberValue(5)),
            null
        )
        val printLnType = PrintLnType(divisionType)
        val interpreter = InterpreterImpl()
        val dynamicVisitor = DynamicInterpreterVisitor(interpreter)
        val staticVisitor = StaticInterpreterVisitor(interpreter)
        divisionType.visit(dynamicVisitor)
        printLnType.visit(staticVisitor)
    }
}
