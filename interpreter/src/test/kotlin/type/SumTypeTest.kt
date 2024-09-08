package type

import interpreter.InterpreterProvider
import node.PrimType
import node.dynamic.LiteralType
import node.dynamic.LiteralValue
import node.dynamic.MultiplyType
import node.dynamic.SumType
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
    fun testSumBoolResult() {
        val assignationType = AssignationType(
            DeclarationType(
                ModifierType("let", true),
                IdentifierType(PrimType.BOOLEAN),
                "a"
            ),
            LiteralType(LiteralValue.BooleanValue(true))
        )
        val multiplyType = ExpressionType(
            VariableType("a", null, true),
            MultiplyType(
                LiteralType(LiteralValue.BooleanValue(true)),
                VariableType("a", null, true),
                null
            )
        )
        val interpreter = InterpreterProvider(
            listOf(assignationType, multiplyType).iterator()
        ).provideInterpreter("1.0")
        assertThrows<IllegalArgumentException> {
            interpreter.execute()
        }
    }

    @Test
    fun testSumNumberStringResult() {
        val assignationType = AssignationType(
            DeclarationType(
                ModifierType("let", true),
                IdentifierType(PrimType.STRING),
                "a"
            ),
            SumType(
                LiteralType(LiteralValue.NumberValue(5)),
                LiteralType(LiteralValue.StringValue("5")),
                null
            )
        )
        val printLnType = PrintLnType(VariableType("a", null, true))
        val interpreter = InterpreterProvider(
            listOf(assignationType, printLnType).iterator()
        ).provideInterpreter("1.0")
        val output: List<String> = interpreter.execute()
        assertEquals(listOf("55"), output)
    }

    @Test
    fun testSumNumber() {
        val assignationType = AssignationType(
            DeclarationType(
                ModifierType("let", true),
                IdentifierType(PrimType.NUMBER),
                "a"
            ),
            SumType(
                LiteralType(LiteralValue.NumberValue(5)),
                LiteralType(LiteralValue.NumberValue(5)),
                null
            )
        )
        val printLnType = PrintLnType(VariableType("a", null, true))
        val interpreter = InterpreterProvider(
            listOf(assignationType, printLnType).iterator()
        ).provideInterpreter("1.0")
        val output: List<String> = interpreter.execute()
        assertEquals(listOf("10"), output)
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
        val sumType = ExpressionType(
            VariableType("a", null, true),
            SumType(
                LiteralType(LiteralValue.NumberValue(1)),
                VariableType("a", null, true),
                null
            )
        )
        val printLnType = PrintLnType(VariableType("a", null, true))
        val interpreter = InterpreterProvider(
            listOf(assignationType, sumType, printLnType).iterator()
        ).provideInterpreter("1.0")
        val output: List<String> = interpreter.execute()
        assertEquals(listOf("6"), output)
    }
}
