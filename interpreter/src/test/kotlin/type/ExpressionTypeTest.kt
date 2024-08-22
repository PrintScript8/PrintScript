package type

import interpreter.InterpreterImpl
import node.dynamic.VariableType
import node.staticpkg.*
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows

class ExpressionTypeTest {
    //Test for expressionType
    @Test
    fun `expressionType val test`() {
        val interpreter = InterpreterImpl()
        val asssignationTypeA = AssignationType(
            DeclarationType(
                ModifierType(
                    "val",
                    false
                ),
                IdentifierType(),
                "a"
                ),
            LiteralType(LiteralValue.NumberValue(1))
            )
        val asssignationTypeB = AssignationType(
            DeclarationType(
                ModifierType(
                    "var",
                    true
                ),
                IdentifierType(),
                "b"
            ),
            LiteralType(LiteralValue.NumberValue(1))
        )
        val expressionType = ExpressionType(
            VariableType("a", null, false),
            VariableType("b", null, false)
        )
        val list = listOf(asssignationTypeA, asssignationTypeB, expressionType)
        interpreter.execute(list)
        assertThrows<UnsupportedOperationException> {
            interpreter.execute(list)
        }
    }

    @Test
    fun `expressionType var test`() {
        val interpreter = InterpreterImpl()
        val asssignationTypeA = AssignationType(
            DeclarationType(
                ModifierType(
                    "var",
                    true
                ),
                IdentifierType(),
                "a"
            ),
            LiteralType(LiteralValue.NumberValue(1))
        )
        val asssignationTypeB = AssignationType(
            DeclarationType(
                ModifierType(
                    "var",
                    true
                ),
                IdentifierType(),
                "b"
            ),
            LiteralType(LiteralValue.NumberValue(1))
        )
        val expressionType = ExpressionType(
            VariableType("a", null, false),
            VariableType("b", null, false)
        )
        val list = listOf(asssignationTypeA, asssignationTypeB, expressionType)
        interpreter.execute(list)
        assertEquals("1", interpreter.getValue("a").second.toString())
    }
}