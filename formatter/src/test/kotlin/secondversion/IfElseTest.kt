package secondversion

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
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import provider.FormatterProvider

class IfElseTest {

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
        val ifElseType = IfElseType(listOf(ifBranch), boolean, listOf(elseBranch))
        val version = "1.1"
        val list = listOf(
            ifElseType,
            PrintLnType(VariableType("testLeft", null, true))
        )

        val formatter = FormatterProvider(list.iterator()).provideFormatter(version)
        val formatted = formatter.format()
        val expected =
            "if (true) {\n" +
                "    let testLeft: number = 1;\n" +
                "} else {\n" +
                "    let testRight: number = 3;\n" +
                "}\n" +
                "println(testLeft);"

        assertEquals(expected, formatted)
    }
}
