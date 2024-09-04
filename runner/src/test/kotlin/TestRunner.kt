
import node.staticpkg.AssignationType
import org.junit.jupiter.api.Test
import runner.Operations
import kotlin.test.assertEquals
import kotlin.test.assertIs

class TestRunner {
    private val runner = Operations()

    @Test
    fun `Test lexer and parser`() {
        assertIs<AssignationType>(runner.validate("let name: Number = 5;").get(0))
    }

    @Test
    fun `Test interpreter`() {
        assertEquals(runner.execute("let pi: number;\n" +
                "pi = 3.14;\n" +
                "println(pi / 2);\n"), listOf("1.57"))
    }

    @Test
    fun `Test formatter`() {
        assertEquals(runner.format("let name: Number = 5;"), "let name: Number = 5;")
    }

    @Test
    fun `Test linter`() {
        assertEquals(runner.analyze("let name: Number = 5;"), listOf())
    }
}
