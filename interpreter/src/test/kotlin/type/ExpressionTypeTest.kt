package type

import interpreter.InterpreterImpl
import node.PrimType
import node.dynamic.LiteralType
import node.dynamic.LiteralValue
import node.dynamic.VariableType
import node.staticpkg.AssignationType
import node.staticpkg.DeclarationType
import node.staticpkg.ExpressionType
import node.staticpkg.IdentifierType
import node.staticpkg.ModifierType
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows

class ExpressionTypeTest {
    // Test for expressionType
    @Test
    fun `expressionType val test`() {
        val interpreter = InterpreterImpl()
        val assignationTypeA = AssignationType(
            DeclarationType(
                ModifierType(
                    "val",
                    false
                ),
                IdentifierType(PrimType.STRING),
                "a"
            ),
            LiteralType(LiteralValue.NumberValue(1))
        )
        val assignationTypeB = AssignationType(
            DeclarationType(
                ModifierType(
                    "var",
                    true
                ),
                IdentifierType(PrimType.STRING),
                "b"
            ),
            LiteralType(LiteralValue.NumberValue(1))
        )
        val expressionType = ExpressionType(
            VariableType("a", null, false),
            VariableType("b", null, false)
        )
        val list = listOf(assignationTypeA, assignationTypeB, expressionType)
        interpreter.execute(list)
        assertThrows<UnsupportedOperationException> {
            interpreter.execute(list)
        }
    }

    @Test
    fun `expressionType var test`() {
        val interpreter = InterpreterImpl()
        val assignationTypeA = AssignationType(
            DeclarationType(
                ModifierType(
                    "var",
                    true
                ),
                IdentifierType(PrimType.STRING),
                "a"
            ),
            LiteralType(LiteralValue.NumberValue(1))
        )
        val assignationTypeB = AssignationType(
            DeclarationType(
                ModifierType(
                    "var",
                    true
                ),
                IdentifierType(PrimType.STRING),
                "b"
            ),
            LiteralType(LiteralValue.NumberValue(1))
        )
        val expressionType = ExpressionType(
            VariableType("a", null, false),
            VariableType("b", null, false)
        )
        val list = listOf(assignationTypeA, assignationTypeB, expressionType)
        interpreter.execute(list)
        assertEquals("1", interpreter.getValue("a").second.toString())
    }
}
