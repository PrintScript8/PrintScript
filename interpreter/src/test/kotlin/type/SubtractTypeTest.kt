package type

import interpreter.InterpreterImpl
import node.dynamic.SubtractType
import node.dynamic.VariableType
import node.staticpkg.*
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import visitor.InterpreterVisitor

class SubtractTypeTest {

    @Test
    fun testSumTypeCreation() {
        val subtractType = SubtractType(
            LiteralType(LiteralValue.NumberValue(5)),
            LiteralType(LiteralValue.NumberValue(5)),
            null)
        assertNotNull(subtractType)
    }

    @Test
    fun testNumberResult() {
        val subtractType = SubtractType(
            LiteralType(LiteralValue.NumberValue(5)),
            LiteralType(LiteralValue.NumberValue(5)),
            null)
        val dynamicVisitor = InterpreterVisitor(InterpreterImpl())
        subtractType.visit(dynamicVisitor)
        val result: LiteralValue = subtractType.result!!
        assertEquals("0", result.toString())
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
        val subtractType = SubtractType(
            LiteralType(LiteralValue.NumberValue(1)),
            VariableType("a", null, false),
            null)
        val dynamicVisitor = InterpreterVisitor(InterpreterImpl())
        assignationType.visit(dynamicVisitor)
        subtractType.visit(dynamicVisitor)
        val result: LiteralValue = subtractType.result!!
        assertEquals("-4", result.toString())
    }

    @Test
    fun testPrint() {
        val subtractType = SubtractType(
            LiteralType(LiteralValue.NumberValue(1)),
            LiteralType(LiteralValue.NumberValue(5)),
            null)
        val printLnType = PrintLnType(subtractType)
        val dynamicVisitor = InterpreterVisitor(InterpreterImpl())
        subtractType.visit(dynamicVisitor)
        printLnType.visit(dynamicVisitor)
    }
}