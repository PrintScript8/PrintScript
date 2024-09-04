
import node.staticpkg.AssignationType
import org.junit.jupiter.api.Test
import runner.Operations
import java.nio.file.Paths
import kotlin.test.assertEquals
import kotlin.test.assertIs

class TestRunner {
    private val runner = Operations()

    @Test
    fun `Test lexer and parser`() {
        val input = getAbsolutePath("src/test/kotlin/testfile/file1")
        assertIs<AssignationType>(runner.validate(input).get(0))
    }

    @Test
    fun `Test interpreter`() {
        val input = getAbsolutePath("src/test/kotlin/testfile/file2")
        assertEquals(
            runner.execute(input),
            listOf("1.57")
        )
    }

    @Test
    fun `Test formatter`() {
        val input = getAbsolutePath("src/test/kotlin/testfile/file3")
        assertEquals(
            runner.format(input),
            "let something: string = \"a really cool thing\";\n" +
                "let another_thing: string = \"another really cool thing\";\n" +
                "let twice_thing: string = \"another really cool thing twice\";\n" +
                "let third_thing: string = \"another really cool thing three times\";"
        )
    }

    @Test
    fun `Test linter`() {
        val input = getAbsolutePath("src/test/kotlin/testfile/file4")
        assertEquals(runner.analyze(input), listOf())
    }

    private fun getAbsolutePath(relativePath: String): String {
        val projectRoot = Paths.get("").toAbsolutePath().toString()
        return Paths.get(projectRoot, relativePath).toString()
    }
}
