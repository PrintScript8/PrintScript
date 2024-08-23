package type

import interpreter.InterpreterImpl
import node.dynamic.LiteralType
import node.dynamic.LiteralValue
import node.dynamic.SubtractType
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

class SubtractTypeTest {

    @Test
    fun testSumTypeCreation() {
        val subtractType = SubtractType(
            LiteralType(LiteralValue.NumberValue(5)),
            LiteralType(LiteralValue.NumberValue(5)),
            null
        )
        assertNotNull(subtractType)
    }

    @Test
    fun testNumberResult() {
        val subtractType = SubtractType(
            LiteralType(LiteralValue.NumberValue(5)),
            LiteralType(LiteralValue.NumberValue(5)),
            null
        )
        val dynamicVisitor = DynamicInterpreterVisitor(InterpreterImpl())
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
            null
        )
        val interpreter = InterpreterImpl()
        val dynamicVisitor = DynamicInterpreterVisitor(interpreter)
        val staticVisitor = StaticInterpreterVisitor(interpreter)
        assignationType.visit(staticVisitor)
        subtractType.visit(dynamicVisitor)
        val result: LiteralValue = subtractType.result!!
        assertEquals("-4", result.toString())
    }

    @Test
    fun testPrint() {
        val subtractType = SubtractType(
            LiteralType(LiteralValue.NumberValue(1)),
            LiteralType(LiteralValue.NumberValue(5)),
            null
        )
        val printLnType = PrintLnType(subtractType)
        val interpreter = InterpreterImpl()
        val dynamicVisitor = DynamicInterpreterVisitor(interpreter)
        val staticVisitor = StaticInterpreterVisitor(interpreter)
        subtractType.visit(dynamicVisitor)
        printLnType.visit(staticVisitor)
    }
}
