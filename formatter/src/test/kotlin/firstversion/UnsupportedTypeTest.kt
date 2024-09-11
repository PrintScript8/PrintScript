package firstversion

import node.PrimType
import node.dynamic.LiteralType
import node.dynamic.LiteralValue
import node.dynamic.VariableType
import node.staticpkg.AssignationType
import node.staticpkg.DeclarationType
import node.staticpkg.IdentifierType
import node.staticpkg.IfElseType
import node.staticpkg.ModifierType
import node.staticpkg.PrintLnType
import org.junit.jupiter.api.Assertions.assertThrows
import org.junit.jupiter.api.Test
import provider.FormatterProvider

class UnsupportedTypeTest {

    @Test
    fun `if-else test`() {

        val ifBranch = AssignationType(
            DeclarationType(
                ModifierType("let", true),
                IdentifierType(PrimType.NUMBER),
                "testLeft"
            ),
            LiteralType(LiteralValue.NumberValue(1))
        )
        val elseBranch = AssignationType(
            DeclarationType(
                ModifierType("let", true),
                IdentifierType(PrimType.NUMBER),
                "testRight"
            ),
            LiteralType(LiteralValue.NumberValue(3))
        )

        val boolean = LiteralType(LiteralValue.BooleanValue(true))
        val ifElseType = IfElseType(ifBranch, boolean, elseBranch)
        val version = "1.0"
        val list = listOf(
            ifElseType,
            PrintLnType(VariableType("testLeft", null, true))
        )

        val formatter = FormatterProvider(list.iterator()).provideFormatter(version)

        assertThrows(IllegalArgumentException::class.java) {
            formatter.format()
        }
    }
}
