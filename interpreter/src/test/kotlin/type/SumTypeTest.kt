package type

import interpreter.InterpreterImpl
import node.PrimType
import node.dynamic.LiteralType
import node.dynamic.LiteralValue
import node.dynamic.SumType
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

class SumTypeTest {

    @Test
    fun testSumTypeCreation() {
        val sumType = SumType(
            LiteralType(LiteralValue.NumberValue(5)),
            LiteralType(LiteralValue.NumberValue(5)),
            null
        )
        assertNotNull(sumType)
    }

    @Test
    fun testSumNumberResult() {
        val sumType = SumType(
            LiteralType(LiteralValue.NumberValue(5)),
            LiteralType(LiteralValue.NumberValue(5)),
            null
        )
        val dynamicVisitor = DynamicInterpreterVisitor(InterpreterImpl())
        sumType.visit(dynamicVisitor)
        val result: LiteralValue = sumType.result!!
        assertEquals("10", result.toString())
    }

    @Test
    fun testSumNumberStringResult() {
        val sumType = SumType(
            LiteralType(LiteralValue.NumberValue(5)),
            LiteralType(LiteralValue.StringValue("5")),
            null
        )
        val dynamicVisitor = DynamicInterpreterVisitor(InterpreterImpl())
        sumType.visit(dynamicVisitor)
        val result: LiteralValue = sumType.result!!
        assertEquals("55", result.toString())
    }

    @Test
    fun testSumStringResult() {
        val sumType = SumType(
            LiteralType(LiteralValue.StringValue("Hello ")),
            LiteralType(LiteralValue.StringValue("World!")),
            null
        )
        val dynamicVisitor = DynamicInterpreterVisitor(InterpreterImpl())
        sumType.visit(dynamicVisitor)
        val result: LiteralValue = sumType.result!!
        assertEquals("Hello World!", result.toString())
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
        val sumType = SumType(
            LiteralType(LiteralValue.NumberValue(1)),
            VariableType("a", null, false),
            null
        )
        val interpreter = InterpreterImpl()
        val dynamicVisitor = DynamicInterpreterVisitor(interpreter)
        val staticVisitor = StaticInterpreterVisitor(interpreter)
        assignationType.visit(staticVisitor)
        sumType.visit(dynamicVisitor)
        val result: LiteralValue = sumType.result!!
        assertEquals("6", result.toString())
    }

    @Test
    fun testPrint() {
        val sumType = SumType(
            LiteralType(LiteralValue.NumberValue(1)),
            LiteralType(LiteralValue.NumberValue(5)),
            null
        )
        val printLnType = PrintLnType(sumType)
        val interpreter = InterpreterImpl()
        val dynamicVisitor = DynamicInterpreterVisitor(interpreter)
        val staticVisitor = StaticInterpreterVisitor(interpreter)
        sumType.visit(dynamicVisitor)
        printLnType.visit(staticVisitor)
    }
}
