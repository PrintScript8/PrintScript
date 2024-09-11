import linter.Linter
import linter.LinterProvider
import node.PrimType
import node.dynamic.LiteralType
import node.dynamic.LiteralValue
import node.dynamic.ReadInputType
import node.dynamic.SumType
import node.staticpkg.AssignationType
import node.staticpkg.DeclarationType
import node.staticpkg.IdentifierType
import node.staticpkg.ModifierType
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

class ReadInputTest {
    private val linter1: Linter = LinterProvider().provideLinter(
        "{ \"mandatory-variable-or-literal-in-readInput\": \"true\"}",
        "1.0"
    )
    private val linter2: Linter = LinterProvider().provideLinter(
        "{ \"mandatory-variable-or-literal-in-readInput\": \"true\"}",
        "1.1"
    )

    @Test
    fun testReadInput() {
        val root = AssignationType(
            DeclarationType(
                ModifierType("val", false),
                IdentifierType(PrimType.STRING),
                "myVariable"
            ),
            ReadInputType(
                LiteralType(
                    LiteralValue.StringValue("Hello World!")
                ),
                null
            )
        )
        assertEquals(linter1.lint(listOf(root).iterator()), emptyList<Any>())
        assertEquals(linter2.lint(listOf(root).iterator()), emptyList<Any>())
    }

    @Test
    fun testReadInputWithVariable() {
        val root = AssignationType(
            DeclarationType(
                ModifierType("val", false),
                IdentifierType(PrimType.STRING),
                "myVariable"
            ),
            ReadInputType(
                SumType(
                    LiteralType(
                        LiteralValue.StringValue("Hello")
                    ),
                    LiteralType(
                        LiteralValue.StringValue("World!")
                    ),
                    null
                ),
                null
            )
        )
        assertEquals(
            linter2.lint(listOf(root).iterator()).first().toString(),
            "Error(type=ERROR, message='Only literal or variable types are allowed as argument')"
        )
        assertEquals(linter1.lint(listOf(root).iterator()), emptyList<Any>())
    }
}
