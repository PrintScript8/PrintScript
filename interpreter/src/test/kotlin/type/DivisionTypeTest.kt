package type

import interpreter.InterpreterImpl
import node.dynamic.DivisionType
import node.dynamic.VariableType
import node.staticpkg.*
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import visitor.InterpreterVisitor

class DivisionTypeTest {

    @Test
    fun testSumTypeCreation() {
        val divisionType = DivisionType(LiteralType(LiteralValue.NumberValue(1))
                ,LiteralType(LiteralValue.NumberValue(1)), null)
        assertNotNull(divisionType)
    }

    @Test
    fun testNumberResult() {
        val divisionType = DivisionType(
            LiteralType(LiteralValue.NumberValue(1)),
            LiteralType(LiteralValue.NumberValue(5)),
         null)
        val dynamicVisitor = InterpreterVisitor(InterpreterImpl())
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
            null)
        val dynamicVisitor = InterpreterVisitor(InterpreterImpl())
        assignationType.visit(dynamicVisitor)
        divisionType.visit(dynamicVisitor)
        val result: LiteralValue = divisionType.result!!
        assertEquals("0.2", result.toString())
    }

    @Test
    fun testPrint() {
        val divisionType = DivisionType(
            LiteralType(LiteralValue.NumberValue(1)),
            LiteralType(LiteralValue.NumberValue(5)),
            null)
        val printLnType = PrintLnType(divisionType)
        val dynamicVisitor = InterpreterVisitor(InterpreterImpl())
        divisionType.visit(dynamicVisitor)
        printLnType.visit(dynamicVisitor)
    }
}