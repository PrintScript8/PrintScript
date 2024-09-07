
import node.staticpkg.AssignationType
import org.junit.jupiter.api.Test
import runner.Operations
import java.io.File
import java.nio.file.Paths
import kotlin.test.assertEquals
import kotlin.test.assertIs

class TestRunner {

    @Test
    fun `Test lexer and parser`() {
        val input = readFile(File(getAbsolutePath("src/test/kotlin/testfile/file1")))
        val runner = Operations(input, "1.0")
        assertIs<AssignationType>(runner.validate().get(0))
    }

    @Test
    fun `Test interpreter`() {
        val input = readFile(File(getAbsolutePath("src/test/kotlin/testfile/file2")))
        val runner = Operations(input, "1.0")
        assertEquals(
            runner.execute(),
            listOf("1.57")
        )
    }

    @Test
    fun `Test formatter`() {
        val input = readFile(File(getAbsolutePath("src/test/kotlin/testfile/file3")))
        val runner = Operations(input, "1.0")
        assertEquals(
            runner.format(),
            "let something: string = \"a really cool thing\";\n" +
                "let another_thing: string = \"another really cool thing\";\n" +
                "let twice_thing: string = \"another really cool thing twice\";\n" +
                "let third_thing: string = \"another really cool thing three times\";"
        )
    }

    @Test
    fun `Test linter`() {
        val input = readFile(File(getAbsolutePath("src/test/kotlin/testfile/file4")))
        val runner = Operations(input, "1.0")
        assertEquals(runner.analyze(), listOf())
    }

    private fun getAbsolutePath(relativePath: String): String {
        val projectRoot = Paths.get("").toAbsolutePath().toString()
        return Paths.get(projectRoot, relativePath).toString()
    }

    private fun readFile(file: File): String {
        return file.readText()
    }
}
