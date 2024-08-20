import formatter.FormatterImpl
import node.staticpkg.*
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import type.LiteralType
import type.LiteralValue

class AssignationTest {

    private val formatter = FormatterImpl()

    @Test
    fun testAssignation() {

        val nameDeclaration = DeclarationType(
            ModifierType("val", false),
            IdentifierType(),
            "name"
        )

        val variableDeclaration = DeclarationType(
            ModifierType("let", true),
            IdentifierType(),
            "i"
        )

        val stringName = LiteralType(LiteralValue.StringValue("Tomy"))
        val numberZero = LiteralType(LiteralValue.NumberValue(0))

        val assignationTree: StaticNode =
            AssignationType(
                nameDeclaration,
                stringName,
            )

        val assignationTree2: StaticNode =
            AssignationType(
                variableDeclaration,
                numberZero,
            )

        formatter.execute(listOf(assignationTree, assignationTree2))

        val result: String = formatter.getOutput()
        val expected = "val name: Identifier = Tomy;\nlet i: Identifier = 0;"

        assertEquals(expected, result)
    }
}