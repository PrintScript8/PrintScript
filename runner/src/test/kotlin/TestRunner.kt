
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
        assertEquals(
            runner.execute(
                "let pi: number;\n" +
                    "pi = 3.14;\n" +
                    "println(pi / 2);\n"
            ),
            listOf("1.57")
        )
    }

    @Test
    fun `Test formatter`() {
        assertEquals(runner.format("let something: string= \"a really cool thing\";\n" +
                "let another_thing: string =\"another really cool thing\";\n" +
                "let twice_thing: string=\"another really cool thing twice\";\n" +
                "let third_thing: string = \"another really cool thing three times\";"),
            "let something: string = \"a really cool thing\";\n" +
                    "let another_thing: string = \"another really cool thing\";\n" +
                    "let twice_thing: string = \"another really cool thing twice\";\n" +
                    "let third_thing: string = \"another really cool thing three times\";")
    }

    @Test
    fun `Test linter`() {
        assertEquals(runner.analyze("let name: Number = 5;"), listOf())
    }
}
