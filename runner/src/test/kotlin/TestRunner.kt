import node.staticpkg.AssignationType
import node.staticpkg.PrintLnType
import org.junit.jupiter.api.Test
import runner.Operations
import java.io.File
import java.nio.file.Paths
import java.util.Collections.emptyIterator
import kotlin.test.assertEquals
import kotlin.test.assertIs

class TestRunner {

    private val provider: Iterator<String> = emptyIterator()

    @Test
    fun `Test lexer and parser`() {
        val input = readFile(File(getAbsolutePath("src/test/kotlin/testfile/file1")))
        val runner = Operations(input.byteInputStream(), "1.0", provider)
        assertIs<AssignationType>(runner.validate()[0])
    }

    @Test
    fun `Test interpreter`() {
        val input = readFile(File(getAbsolutePath("src/test/kotlin/testfile/file2")))
        val runner = Operations(input.byteInputStream(), "1.1", provider)
        val output = runner.execute()
        assertEquals(
            "1.57",
            output.next()
        )
    }

    @Test
    fun `Test formatter`() {
        val input = readFile(File(getAbsolutePath("src/test/kotlin/testfile/file3")))
        val runner = Operations(input.byteInputStream(), "1.0", provider)
        val rules = """{
        "rules": {
            "spaceBeforeColon": false,
            "spaceAfterColon": true,
            "spaceAroundEquals": true,
            "newlineBeforePrintln": 2,
            "newlineAfterSemicolon": true
        }
    }"""
        assertEquals(
            runner.format(rules),
            "let something: string = \"a really cool thing\";\n" +
                "let another_thing: string = \"another really cool thing\";\n" +
                "let twice_thing: string = \"another really cool thing twice\";\n" +
                "let third_thing: string = \"another really cool thing three times\";"
        )
    }

    @Test
    fun `Test linter`() {
        val input = readFile(File(getAbsolutePath("src/test/kotlin/testfile/file4")))
        val runner = Operations(input.byteInputStream(), "1.0", provider)
        assertEquals(runner.analyze("{}"), listOf())
    }

    private fun getAbsolutePath(relativePath: String): String {
        val projectRoot = Paths.get("").toAbsolutePath().toString()
        return Paths.get(projectRoot, relativePath).toString()
    }

    private fun readFile(file: File): String {
        return file.readText()
    }

    @Test
    fun testPrint() {
        val input = "println(\"This is a text\");\n" +
            "println(\"This is a text\");\n" +
            "println(\"This is a text\");\n" +
            "println(\"This is a text\");\n" +
            "println(\"This is a text\");"
        val runner = Operations(input.byteInputStream(), "1.0", provider)
        assertIs<PrintLnType>(runner.validate().get(0))
    }
}
