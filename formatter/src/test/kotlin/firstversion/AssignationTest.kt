package firstversion

import node.PrimType
import node.dynamic.LiteralType
import node.dynamic.LiteralValue
import node.staticpkg.AssignationType
import node.staticpkg.DeclarationType
import node.staticpkg.IdentifierType
import node.staticpkg.ModifierType
import node.staticpkg.StaticNode
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import provider.FormatterProvider

class AssignationTest {

    @Test
    fun testAssignation() {
        val nameDeclaration = DeclarationType(
            ModifierType("val", false),
            IdentifierType(PrimType.STRING),
            "name"
        )

        val variableDeclaration = DeclarationType(
            ModifierType("let", true),
            IdentifierType(PrimType.NUMBER),
            "i"
        )

        val stringName = LiteralType(LiteralValue.StringValue("Tomy"))
        val numberZero = LiteralType(LiteralValue.NumberValue(0))

        val assignationTree: StaticNode =
            AssignationType(
                nameDeclaration,
                stringName
            )

        val assignationTree2: StaticNode =
            AssignationType(
                variableDeclaration,
                numberZero
            )

        val astList = listOf(assignationTree, assignationTree2)
        val formatter = FormatterProvider(astList.iterator()).provideFormatter("1.0")
        val result = formatter.format()
        val expected = "val name: string = \"Tomy\";\n" +
            "let i: number = 0;"

        assertEquals(expected, result)
    }
}
