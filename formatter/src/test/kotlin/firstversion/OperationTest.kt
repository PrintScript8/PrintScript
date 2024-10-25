package firstversion

import node.PrimType
import node.dynamic.DivisionType
import node.dynamic.LiteralType
import node.dynamic.LiteralValue
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
import kotlin.test.assertEquals

class OperationTest {

    @Test
    fun testSum() {
        val declaration = DeclarationType(
            ModifierType("let", true),
            IdentifierType(PrimType.NUMBER),
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

        val astList = listOf(assignation)
        val formatter = FormatterProvider(astList.iterator()).provideFormatter("1.0")
        val result = formatter.format()
        val expected = "let a: number = 5 + 5;"

        assertEquals(expected, result)
    }

    @Test
    fun testSubtract() {
        val declaration = DeclarationType(
            ModifierType("let", true),
            IdentifierType(PrimType.NUMBER),
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

        val astList = listOf(assignation)
        val formatter = FormatterProvider(astList.iterator()).provideFormatter("1.0")
        val result = formatter.format()
        val expected = "let a: number = 5 - 5;"

        assertEquals(expected, result)
    }

    @Test
    fun testMultiply() {
        val declaration = DeclarationType(
            ModifierType("let", true),
            IdentifierType(PrimType.NUMBER),
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

        val astList = listOf(assignation)
        val formatter = FormatterProvider(astList.iterator()).provideFormatter("1.0")
        val result = formatter.format()
        val expected = "let a: number = 5 * 5;"

        assertEquals(expected, result)
    }

    @Test
    fun testDivision() {
        val declaration = DeclarationType(
            ModifierType("let", true),
            IdentifierType(PrimType.NUMBER),
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

        val astList = listOf(assignation)
        val formatter = FormatterProvider(astList.iterator()).provideFormatter("1.0")
        val result = formatter.format()
        val expected = "let a: number = 5 / 5;"

        assertEquals(expected, result)
    }

    @Test
    fun testSumWithVariable() {
        val declaration = DeclarationType(
            ModifierType("let", true),
            IdentifierType(PrimType.NUMBER),
            "a"
        )

        val assignationType = AssignationType(
            declaration,
            LiteralType(LiteralValue.NumberValue(5))
        )

        val multiplyType = SumType(
            LiteralType(LiteralValue.NumberValue(1)),
            VariableType("a", null),
            null
        )

        val assignation: StaticNode = AssignationType(
            declaration,
            multiplyType
        )

        val astList = listOf(assignationType, assignation)
        val formatter = FormatterProvider(astList.iterator()).provideFormatter("1.0")
        val result = formatter.format()
        val expected = "let a: number = 5;\n" +
            "let a: number = 1 + a;"

        assertEquals(expected, result)
    }

    @Test
    fun multipleOperation() {
        val declaration = DeclarationType(
            ModifierType("let", true),
            IdentifierType(PrimType.NUMBER),
            "a"
        )

        val sumType = SumType(
            LiteralType(LiteralValue.NumberValue(1)),
            VariableType("\"x\"", null),
            null
        )

        val sumType2 = SumType(
            LiteralType(LiteralValue.NumberValue(1)),
            VariableType("a", null),
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

        val astList = listOf(assignation, assignation2)
        val formatter = FormatterProvider(astList.iterator()).provideFormatter("1.0")
        val result = formatter.format()
        val expected =
            "let a: number = 1 + \"x\" * 5;\n" +
                "let a: number = 1 + a * 5;"

        assertEquals(expected, result)
    }
}
