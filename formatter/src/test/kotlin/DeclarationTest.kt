import formatter.FormatterImpl
import node.staticpkg.DeclarationType
import node.staticpkg.IdentifierType
import node.staticpkg.ModifierType
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

class DeclarationTest {

    private val formatter = FormatterImpl()

    @Test
    fun declarationTest() {
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

        val result = formatter.execute(listOf(nameDeclaration, variableDeclaration))
        val expected = "val name: Identifier;\n" +
                "let i: Identifier;"

        assertEquals(expected, result)
    }
}