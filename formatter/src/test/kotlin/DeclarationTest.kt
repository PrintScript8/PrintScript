import formatter.FormatterImpl
import node.PrimType
import node.staticpkg.DeclarationType
import node.staticpkg.IdentifierType
import node.staticpkg.ModifierType
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

class DeclarationTest {

    private val formatter = FormatterImpl()

    @Test
    fun declaration() {
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

        val result = formatter.execute(listOf(nameDeclaration, variableDeclaration))
        val expected = "val name: string;\n" +
            "let i: string;"

        assertEquals(expected, result)
    }
}
