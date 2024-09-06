package type

import interpreter.IntepreterProvider
import node.PrimType
import node.dynamic.DivisionType
import node.dynamic.LiteralType
import node.dynamic.LiteralValue
import node.dynamic.VariableType
import node.staticpkg.AssignationType
import node.staticpkg.DeclarationType
import node.staticpkg.ExpressionType
import node.staticpkg.IdentifierType
import node.staticpkg.ModifierType
import node.staticpkg.PrintLnType
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertNotNull
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows

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
    fun testDivideBoolResult() {
        val assignationType = AssignationType(
            DeclarationType(
                ModifierType("let", true),
                IdentifierType(PrimType.NUMBER),
                "a"
            ),
            LiteralType(LiteralValue.BooleanValue(true))
        )
        val divisionType = ExpressionType(
            VariableType("a", null, true),
            DivisionType(
                LiteralType(LiteralValue.NumberValue(1)),
                VariableType("a", null, true),
                null
            )
        )
        val printLnType = PrintLnType(VariableType("a", null, true))
        val interpreter = IntepreterProvider().provideInterpreter("1.0")
        assertThrows<IllegalArgumentException> {
            interpreter.execute(listOf(assignationType, divisionType, printLnType))
        }
    }

    // ATENTION PLEASE! TEST IS INDIFFERENT TO PRIMTYPE
    @Test
    fun testWithVariable() {
        val assignationType = AssignationType(
            DeclarationType(
                ModifierType("let", true),
                IdentifierType(PrimType.NUMBER),
                "a"
            ),
            LiteralType(LiteralValue.NumberValue(5))
        )
        val divisionType = ExpressionType(
            VariableType("a", null, true),
            DivisionType(
                LiteralType(LiteralValue.NumberValue(1)),
                VariableType("a", null, true),
                null
            )
        )
        val printLnType = PrintLnType(VariableType("a", null, true))
        val interpreter = IntepreterProvider().provideInterpreter("1.0")
        val output: List<String> = interpreter.execute(listOf(assignationType, divisionType, printLnType))
        assertEquals(listOf("0.2"), output)
    }
}
