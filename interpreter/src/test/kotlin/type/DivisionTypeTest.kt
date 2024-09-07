package type

import interpreter.InterpreterImpl
import node.PrimType
import node.Node
import node.dynamic.DivisionType
import node.dynamic.LiteralType
import node.dynamic.LiteralValue
import node.dynamic.VariableType
import node.staticpkg.*
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertNotNull
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
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
        val nodeList: List<Node> = listOf(divisionType)
        val iterator: Iterator<Node> = nodeList.iterator()
        val dynamicVisitor = DynamicInterpreterVisitor(InterpreterImpl(iterator))
        divisionType.visit(dynamicVisitor)
        val result: LiteralValue = divisionType.result!!
        assertEquals("0.2", result.toString())
    }

    @Test
    fun testDivideBoolResult() {
        val divisionType = DivisionType(
            LiteralType(LiteralValue.NumberValue(5)),
            LiteralType(LiteralValue.BooleanValue(false)),
            null
        )
        val dynamicVisitor = DynamicInterpreterVisitor(InterpreterImpl())
        assertThrows<IllegalArgumentException> {
            divisionType.visit(dynamicVisitor)
        }
    }

    @Test
    fun testWithVariable() {
        val assignationType = AssignationType(
            DeclarationType(
                ModifierType("let", true),
                IdentifierType(PrimType.STRING),
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
