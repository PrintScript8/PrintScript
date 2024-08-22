import formatter.FormatterImpl
import node.dynamic.DivisionType
import node.dynamic.MultiplyType
import node.dynamic.SubtractType
import node.dynamic.SumType
import node.dynamic.VariableType
import node.staticpkg.AssignationType
import node.staticpkg.DeclarationType
import node.staticpkg.IdentifierType
import node.staticpkg.ModifierType
import node.staticpkg.StaticNode
import org.junit.jupiter.api.Test
import type.LiteralType
import type.LiteralValue
import kotlin.test.assertEquals

class OperationTest {

    private val formatter = FormatterImpl()

    @Test
    fun testSum() {
        val declaration = DeclarationType(
            ModifierType("let", true),
            IdentifierType(),
            "a"
        )

        val multiplyType = SumType(
            LiteralType(LiteralValue.NumberValue(5)),
            LiteralType(LiteralValue.NumberValue(5)),
            null
        )

        val assignation: StaticNode = AssignationType(
            declaration,
            multiplyType
        )

        val result = formatter.execute(listOf(assignation))
        val expected = "let a: Identifier = 5 + 5;"

        assertEquals(expected, result)
    }

    @Test
    fun testSubtract() {
        val declaration = DeclarationType(
            ModifierType("let", true),
            IdentifierType(),
            "a"
        )

        val subtractType = SubtractType(
            LiteralType(LiteralValue.NumberValue(5)),
            LiteralType(LiteralValue.NumberValue(5)),
            null
        )

        val assignation: StaticNode = AssignationType(
            declaration,
            subtractType
        )

        val result = formatter.execute(listOf(assignation))
        val expected = "let a: Identifier = 5 - 5;"

        assertEquals(expected, result)
    }

    @Test
    fun testMultiply() {
        val declaration = DeclarationType(
            ModifierType("let", true),
            IdentifierType(),
            "a"
        )

        val multiplyType = MultiplyType(
            LiteralType(LiteralValue.NumberValue(5)),
            LiteralType(LiteralValue.NumberValue(5)),
            null
        )

        val assignation: StaticNode = AssignationType(
            declaration,
            multiplyType
        )

        val result = formatter.execute(listOf(assignation))
        val expected = "let a: Identifier = 5 * 5;"

        assertEquals(expected, result)
    }

    @Test
    fun testDivision() {
        val declaration = DeclarationType(
            ModifierType("let", true),
            IdentifierType(),
            "a"
        )

        val divisionType = DivisionType(
            LiteralType(LiteralValue.NumberValue(5)),
            LiteralType(LiteralValue.NumberValue(5)),
            null
        )

        val assignation: StaticNode = AssignationType(
            declaration,
            divisionType
        )

        val result = formatter.execute(listOf(assignation))
        val expected = "let a: Identifier = 5 / 5;"

        assertEquals(expected, result)
    }

    @Test
    fun testSumWithVariable() {
        val declaration = DeclarationType(
            ModifierType("let", true),
            IdentifierType(),
            "a"
        )

        val assignationType = AssignationType(
            declaration,
            LiteralType(LiteralValue.NumberValue(5))
        )

        val multiplyType = SumType(
            LiteralType(LiteralValue.NumberValue(1)),
            VariableType("a", null, false),
            null
        )

        val assignation: StaticNode = AssignationType(
            declaration,
            multiplyType
        )

        val result = formatter.execute(listOf(assignationType, assignation))
        val expected = "let a: Identifier = 5;\n" +
            "let a: Identifier = 1 + a;"

        assertEquals(expected, result)
    }

    @Test
    fun multipleOperation() {
        val declaration = DeclarationType(
            ModifierType("let", true),
            IdentifierType(),
            "a"
        )

        val sumType = SumType(
            LiteralType(LiteralValue.NumberValue(1)),
            VariableType("\"x\"", null, false),
            null
        )

        val sumType2 = SumType(
            LiteralType(LiteralValue.NumberValue(1)),
            VariableType("a", null, false),
            null
        )

        val multiplyType = MultiplyType(
            sumType,
            LiteralType(LiteralValue.NumberValue(5)),
            null
        )

        val multiplyType2 = MultiplyType(
            sumType2,
            LiteralType(LiteralValue.NumberValue(5)),
            null
        )

        val assignation: StaticNode = AssignationType(
            declaration,
            multiplyType
        )

        val assignation2: StaticNode = AssignationType(
            declaration,
            multiplyType2
        )

        val result = formatter.execute(listOf(assignation, assignation2))
        val expected =
            "let a: Identifier = 1 + \"x\" * 5;\n" +
                "let a: Identifier = 1 + a * 5;"

        assertEquals(expected, result)
    }
}
