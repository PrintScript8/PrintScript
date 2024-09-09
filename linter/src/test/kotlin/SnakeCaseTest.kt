import linter.Linter
import linter.LinterProvider
import node.PrimType
import node.dynamic.LiteralType
import node.dynamic.LiteralValue
import node.staticpkg.AssignationType
import node.staticpkg.DeclarationType
import node.staticpkg.IdentifierType
import node.staticpkg.ModifierType
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

class SnakeCaseTest {
    private val linter1 = LinterProvider().provideLinter("{ \"identifier_format\": \"snake case\"}", "1.0")
    private val linter2: Linter = LinterProvider().provideLinter(
        "{ \"identifier_format\": \"snake case\"}",
        "1.1"
    )

    @Test
    fun testCorrectSnakeCase() {
        val root = AssignationType(
            DeclarationType(
                ModifierType("val", false),
                IdentifierType(PrimType.STRING),
                "my_created_variable"
            ),
            LiteralType(LiteralValue.StringValue("camelCase"))
        )

        assertEquals(linter1.lint(listOf(root).iterator()), emptyList<Any>())
        assertEquals(linter1.lint(listOf(root).iterator()), linter2.lint(listOf(root).iterator()))
    }

    @Test
    fun testCamelCase() {
        val root = AssignationType(
            DeclarationType(
                ModifierType("val", false),
                IdentifierType(PrimType.STRING),
                "myCreatedVariable"
            ),
            LiteralType(LiteralValue.StringValue("camelCase"))
        )
        assertEquals(
            linter1.lint(listOf(root).iterator()).first().toString(),
            "Error(type=TYPO, message='Declaration name myCreatedVariable is not in snake_case')"
        )
        assertEquals(linter1.lint(listOf(root).iterator()).first().toString(),
            linter2.lint(listOf(root).iterator()).first().toString()
        )
    }

    @Test
    fun testPascalCase() {
        val root = AssignationType(
            DeclarationType(
                ModifierType("val", false),
                IdentifierType(PrimType.STRING),
                "MyCreatedVariable"
            ),
            LiteralType(LiteralValue.StringValue("camelCase"))
        )
        assertEquals(
            linter1.lint(listOf(root).iterator()).first().toString(),
            "Error(type=TYPO, message='Declaration name MyCreatedVariable is not in snake_case')"
        )
        assertEquals(linter1.lint(listOf(root).iterator()).first().toString(),
            linter2.lint(listOf(root).iterator()).first().toString()
        )
    }

    @Test
    fun testKebabCase() {
        val root = AssignationType(
            DeclarationType(
                ModifierType("val", false),
                IdentifierType(PrimType.STRING),
                "my-created-variable"
            ),
            LiteralType(LiteralValue.StringValue("camelCase"))
        )
        assertEquals(
            linter1.lint(listOf(root).iterator()).first().toString(),
            "Error(type=TYPO, message='Declaration name my-created-variable is not in snake_case')"
        )
        assertEquals(linter1.lint(listOf(root).iterator()).first().toString(),
            linter2.lint(listOf(root).iterator()).first().toString()
        )
    }
}
