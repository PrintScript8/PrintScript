import linter.LinterProvider
import node.dynamic.LiteralType
import node.dynamic.LiteralValue
import node.staticpkg.AssignationType
import node.staticpkg.DeclarationType
import node.staticpkg.IdentifierType
import node.staticpkg.ModifierType
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

class SnakeCaseTest {
    private val linter = LinterProvider().provideLinter("{ \"case\": \"snakeCase\" , \"argument\": \"literal\" }")

    @Test
    fun testCorrectSnakeCase() {
        val root = AssignationType(
            DeclarationType(
                ModifierType("val", false),
                IdentifierType(),
                "my_created_variable"
            ),
            LiteralType(LiteralValue.StringValue("camelCase"))
        )

        assertEquals(linter.lint(listOf(root)), emptyList<Any>())
    }

    @Test
    fun testCamelCase() {
        val root = AssignationType(
            DeclarationType(
                ModifierType("val", false),
                IdentifierType(),
                "myCreatedVariable"
            ),
            LiteralType(LiteralValue.StringValue("camelCase"))
        )
        assertEquals(
            linter.lint(listOf(root)).first().toString(),
            "Error(type=TYPO, message='Declaration name myCreatedVariable is not in snake_case')"
        )
    }

    @Test
    fun testPascalCase() {
        val root = AssignationType(
            DeclarationType(
                ModifierType("val", false),
                IdentifierType(),
                "MyCreatedVariable"
            ),
            LiteralType(LiteralValue.StringValue("camelCase"))
        )
        assertEquals(
            linter.lint(listOf(root)).first().toString(),
            "Error(type=TYPO, message='Declaration name MyCreatedVariable is not in snake_case')"
        )
    }

    @Test
    fun testKebabCase() {
        val root = AssignationType(
            DeclarationType(
                ModifierType("val", false),
                IdentifierType(),
                "my-created-variable"
            ),
            LiteralType(LiteralValue.StringValue("camelCase"))
        )
        assertEquals(
            linter.lint(listOf(root)).first().toString(),
            "Error(type=TYPO, message='Declaration name my-created-variable is not in snake_case')"
        )
    }
}
