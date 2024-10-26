package firstversion

import node.PrimType
import node.staticpkg.DeclarationType
import node.staticpkg.IdentifierType
import node.staticpkg.ModifierType
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import provider.FormatterProvider

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

        val rules = """{
        "rules": {
            "spaceBeforeColon": false,
            "spaceAfterColon": true,
            "spaceAroundEquals": true,
            "newlineBeforePrintln": 2,
            "newlineAfterSemicolon": true
        }
    }"""

        val formatter = FormatterProvider().provideFormatter(rules, "1.0")
        val result = formatter.format(astList.iterator())
        val expected = "val name: string;\n" +
            "let i: string;"

        assertEquals(expected, result)
    }
}
