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

        formatter.execute(listOf(nameDeclaration, variableDeclaration))

        val result: String = formatter.getOutput()
        val expected = "val name: Identifier;\nlet i: Identifier;"

        assertEquals(expected, result)
    }
}