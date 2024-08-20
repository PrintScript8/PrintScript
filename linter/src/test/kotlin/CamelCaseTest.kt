import linter.LinterProvider
import node.staticpkg.AssignationType
import node.staticpkg.DeclarationType
import node.staticpkg.IdentifierType
import node.staticpkg.ModifierType
import org.junit.jupiter.api.Assertions.assertEquals
import type.LiteralType
import type.LiteralValue
import kotlin.test.Test

class CamelCaseTest {
    private val linter = LinterProvider().provideLinter("{ \"case\": \"camelCase\" , \"argument\": \"literal\" }")

    @Test
    fun testCorrectCamelCase() {
        val root = AssignationType(
            DeclarationType(
                ModifierType("val", false),
                IdentifierType(),
                "myCreatedVariable"
            ),
            LiteralType(LiteralValue.StringValue("camelCase"))
        )

        assertEquals(linter.lint(listOf(root)), emptyList<Any>())
    }

    @Test
    fun testSnakeCase() {
        val root = AssignationType(
            DeclarationType(
                ModifierType("val", false),
                IdentifierType(),
                "my_created_variable"
            ),
            LiteralType(LiteralValue.StringValue("camelCase"))
        )
        assertEquals(linter.lint(listOf(root)).first().toString(),
            "Error(type=TYPO, message='Declaration name my_created_variable is not in camelCase')")
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
        assertEquals(linter.lint(listOf(root)).first().toString(),
            "Error(type=TYPO, message='Declaration name MyCreatedVariable is not in camelCase')")
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
        assertEquals(linter.lint(listOf(root)).first().toString(),
            "Error(type=TYPO, message='Declaration name my-created-variable is not in camelCase')")
    }
}