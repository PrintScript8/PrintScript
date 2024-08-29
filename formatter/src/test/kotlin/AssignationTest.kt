import formatter.FormatterImpl
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

class AssignationTest {

    private val formatter = FormatterImpl()

    @Test
    fun testAssignation() {
        val nameDeclaration = DeclarationType(
            ModifierType("val", false),
            IdentifierType(PrimType.STRING),
            "name"
        )

        val variableDeclaration = DeclarationType(
            ModifierType("let", true),
            IdentifierType(PrimType.STRING),
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

        val result = formatter.execute(listOf(assignationTree, assignationTree2))
        val expected = "val name: Identifier = \"Tomy\";\n" +
            "let i: Identifier = 0;"

        assertEquals(expected, result)
    }
}
