package type

import interpreter.InterpreterImpl
import node.dynamic.SumType
import node.dynamic.VariableType
import node.staticpkg.*
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
        val sumType = SumType(
            LiteralType(LiteralValue.NumberValue(1)),
            VariableType("a", null, false),
            null)
        val dynamicVisitor = InterpreterVisitor(InterpreterImpl())
        assignationType.visit(dynamicVisitor)
        sumType.visit(dynamicVisitor)
        val result: LiteralValue = sumType.result!!
        assertEquals("6", result.toString())
    }

    @Test
    fun testPrint() {
        val sumType = SumType(
            LiteralType(LiteralValue.NumberValue(1)),
            LiteralType(LiteralValue.NumberValue(5)),
            null)
        val printLnType = PrintLnType(sumType)
        val dynamicVisitor = InterpreterVisitor(InterpreterImpl())
        sumType.visit(dynamicVisitor)
        printLnType.visit(dynamicVisitor)
    }
}