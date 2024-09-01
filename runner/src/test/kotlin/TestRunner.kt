import org.junit.jupiter.api.Test
import kotlin.test.assertEquals

class TestRunner {
    private val runner = Operations()

    @Test
    fun `Test lexer and parser`() {
        assertEquals(runner.validate("let name: Number = 5;"), "Validation successful")
    }

    @Test
    fun `Test interpreter`() {
        assertEquals(runner.execute("let name: Number = 5;", "1.0"), "Result:\n")
    }

    @Test
    fun `Test formatter`() {
        assertEquals(runner.format("let name: Number = 5;", null), "Formatted: let name: Number = 5;")
    }

    @Test
    fun `Test linter`() {
        assertEquals(runner.analyze("let name: Number = 5;"), "No errors found")
    }
}
