package firstversion

import node.PrimType
import node.staticpkg.DeclarationType
import node.staticpkg.IdentifierType
import node.staticpkg.ModifierType
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

class DeclarationTest {

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

        val astList = listOf(nameDeclaration, variableDeclaration)
        val formatter = FormatterProvider(astList.iterator()).provideFormatter("1.0")
        val result = formatter.format()
        val expected = "val name: string;\n" +
            "let i: string;"

        assertEquals(expected, result)
    }
}
