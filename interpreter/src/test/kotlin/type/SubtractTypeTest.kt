package type

import interpreter.InterpreterProvider
import node.PrimType
import node.dynamic.LiteralType
import node.dynamic.LiteralValue
import node.dynamic.SubtractType
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
    fun testBoolResult() {
        val assignationType = AssignationType(
            DeclarationType(
                ModifierType("let", true),
                IdentifierType(PrimType.NUMBER),
                "a"
            ),
            LiteralType(LiteralValue.NumberValue(5))
        )
        val substractType = ExpressionType(
            VariableType("a", null, true),
            SubtractType(
                LiteralType(LiteralValue.NumberValue(5)),
                LiteralType(LiteralValue.BooleanValue(false)),
                null
            )
        )
        val printLnType = PrintLnType(VariableType("a", null, true))
        val interpreter = InterpreterProvider(
            listOf(assignationType, substractType, printLnType).iterator()
        ).provideInterpreter("1.0")
        val output: Iterator<String> = interpreter.iterator()
        assertThrows<IllegalArgumentException> {
            output.asSequence().toList()
        }
    }

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
        val substractType = ExpressionType(
            VariableType("a", null, true),
            SubtractType(
                LiteralType(LiteralValue.NumberValue(1)),
                VariableType("a", null, true),
                null
            )
        )
        val printLnType = PrintLnType(VariableType("a", null, true))
        val interpreter = InterpreterProvider(
            listOf(assignationType, substractType, printLnType).iterator()
        ).provideInterpreter("1.0")
        val output: Iterator<String> = interpreter.iterator()
        assertEquals("-4", output.next())
    }
}
